package benjamin.connector.sonar;

import benjamin.connector.common.LoggingRequestInterceptor;
import benjamin.connector.common.RestHelper;
import benjamin.connector.sonar.model.Project;
import benjamin.connector.sonar.model.SonarConnector;
import benjamin.connector.sonar.model.SonarServer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic connector that connects to version 4 and 5
 */


public class SonarConnectorImpl implements SonarConnector {

    private @interface NeedsInit {
    }

    private static final String PATH_SERVER = "/api/server?format=json";

    private RestHelper restHelper;
    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    private enum Version {
        V4, V5
    }

    private Version version;
    private SonarConnector sonarConnector;

    private String username;
    private String password;
    private String url;

    private SonarConnectorImpl(RestHelper restHelper, RestTemplate restTemplate,
                               String url, String username, String password) {
        this.restHelper = restHelper;
        this.restTemplate = restTemplate;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static SonarConnector build(RestHelper restHelper, RestTemplate restTemplate,
                                       String url, String username, String password) {
        SonarConnectorImpl connectorGeneric = new SonarConnectorImpl(restHelper, restTemplate, url, username, password);

        ProxyFactory pf = new ProxyFactory(new Class[]{SonarConnector.class});
        pf.setTarget(connectorGeneric);
        pf.addAdvice(new MethodInterceptor() {
            public Object invoke(MethodInvocation mi) throws Throwable {
                if (mi.getMethod().isAnnotationPresent(NeedsInit.class)) {
                    SonarConnectorImpl conn = (SonarConnectorImpl) mi.getThis();
                    conn.init();
                    Method redirect = mi.getThis().getClass().getMethod("redirected");
                    mi.getMethod().invoke(conn);
                }
                return null;
            }
        });

        return (SonarConnector) pf.getProxy();
    }


    public RequestEntity makeRequestEntity(final String path) {
        return restHelper.makeRequestEntity(url, path, username, password);
    }

    public SonarServer getServer() {
        return query(PATH_SERVER, SonarServer.class);
    }

    private <E> E query(String url, Class<E> clazz) {
        final RequestEntity<E> re = makeRequestEntity(url);
        final ResponseEntity<E> resp = restTemplate.exchange(re,
                clazz);
        return resp.getBody();
    }

    public void init() {
        SonarServer sonarServer = getServer();
        String serverVersion = sonarServer.getVersion();

        if (serverVersion.startsWith("4")) {
            version = Version.V4;
        } else if (serverVersion.startsWith("5")) {
            version = Version.V5;
        } else {
            throw new RuntimeException("Unknown version " + serverVersion);
        }
    }

    @NeedsInit
    public List<Project> listProjects() {
        if (version == Version.V4) {
            return new Sonar4Connector(this).listProjects();
        } else {
            return new Sonar5Connector(this).listProjects();
        }
    }

    private LoggingRequestInterceptor loggingRequestInterceptor = new LoggingRequestInterceptor();

    @Override
    public void setLogging(boolean state) {
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (interceptors == null) {
            interceptors = new ArrayList<>();
            restTemplate.setInterceptors(interceptors);
        }
        if (state) {
            if (!interceptors.contains(loggingRequestInterceptor)) {
                interceptors.add(loggingRequestInterceptor);
            }
        } else {
            interceptors.remove(loggingRequestInterceptor);
        }

    }


    public <T> ResponseEntity<T> exchange(RequestEntity<T> re, Class<T> aClass) {
        return restTemplate.exchange(re, aClass);
    }

}
