package benjamin.connector.common;

import benjamin.exception.ApplicationException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class RestHelper {

    @Autowired
    private List<HttpMessageConverter<?>> messageConverters;
    private List<ClientHttpRequestInterceptor> interceptors;

    public RequestEntity makeRequestEntity(final String url, String path, final String username, final String
            password) {
        return makeRequestEntity(concat(url, path), username, password);
    }

    private String concat(String url, String path) {
        StringBuffer result = new StringBuffer(url);
        int count = (url.endsWith("/") ? 1 : 0)
                + (path.startsWith("/") ? 1 : 0);
        switch (count) {
            case 0:
                result.append("/");
                result.append(path);
                break;
            case 1:
                result.append(path);
                break;
            case 2:
                result.append(path.substring(1));
        }

        return result.toString();
    }

    public RequestEntity makeRequestEntity(final String url, final String username, final String password) {
        final HttpHeaders headers = makeAuthHeaders(username, password);
        final URI uri = getUri(url);
        return new RequestEntity(headers,
                HttpMethod.GET,
                uri
        );
    }

    private HttpHeaders makeAuthHeaders(final String username, final String password) {
        final HttpHeaders headers = new HttpHeaders();
        if (username != null) {
            final String plainCreds = username + ":" + password;
            final byte[] plainCredsBytes = plainCreds.getBytes();
            final byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
            final String base64Creds = new String(base64CredsBytes);
            headers.add("Authorization", "Basic " + base64Creds);
        }
        return headers;
    }

    private URI getUri(final String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new ApplicationException(e);
        }
    }

    public RestTemplate getRestTemplate() {
        RestTemplate result = new RestTemplate(messageConverters);
        if (interceptors != null) {
            result.setInterceptors(interceptors);
        }
        return result;
    }

    public void setInterceptors(List<ClientHttpRequestInterceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
