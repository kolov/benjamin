package benjamin.connector.sonar;

import benjamin.connector.common.RestHelper;
import benjamin.connector.sonar.model.Project;
import benjamin.connector.sonar.model.SonarServer;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Generic connector that connects to version 4 and 5
 */
public class SonarConnectorGeneric {

    private static final String PATH_SERVER = "/api/server?format=json";

    private enum Version {
        V4, V5
    }

    private Version version;

    private final RestHelper restHelper;

    private String username;
    private String password;
    private String url;

    public SonarConnectorGeneric(RestHelper restHelper, String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.restHelper = restHelper;
    }

    public SonarConnectorGeneric(RestHelper restHelper, SonarSettings sonarsettings) {
        this(restHelper,
                sonarsettings.getSonarUrl(),
                sonarsettings.getSonarUser(),
                sonarsettings.getSonarPassword());
    }

    public RequestEntity makeRequestEntity(final String path) {
        return restHelper.makeRequestEntity(url, path, username, password);
    }

    public SonarServer getServer() {
        return query(PATH_SERVER, SonarServer.class);
    }

    private <E> E query(String url, Class<E> clazz) {
        final RequestEntity<E> re = makeRequestEntity(url);
        final ResponseEntity<E> resp = restHelper.getRestTemplate().exchange(re,
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

    public List<Project> listProjects() {
        checkState(version != null, "Connector must be initialized before use");

        if (version == Version.V4) {
            // don't have 4 yet
            return new Sonar5Connector(this).listProjects();
        } else {
            return new Sonar5Connector(this).listProjects();
        }
    }

    public <T> ResponseEntity<T> exchange(RequestEntity<T> re, Class<T> aClass) {
        return restHelper.getRestTemplate().exchange(re, aClass);
    }


}
