package benjamin.connector.common;

import benjamin.exception.ApplicationException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class RestHelper {


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

}
