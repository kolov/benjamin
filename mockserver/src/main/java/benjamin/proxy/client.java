package benjamin.proxy;

import org.apache.commons.codec.binary.Base64;
import org.mockserver.client.proxy.ProxyClient;

import static org.mockserver.model.HttpRequest.request;


public class Client {

    private static String authHeader(final String username, final String password) {
        final String plainCreds = username + ":" + password;
        final byte[] plainCredsBytes = plainCreds.getBytes();
        final byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        final String base64Creds = new String(base64CredsBytes);
        return "Basic " + base64Creds;
    }

    public static void main(String[] args) {
        new ProxyClient("google.com", 80, "/")
                .dumpToLogAsJava(
                        request()
                                .withMethod("GET")
                                .withHeader("Accept", "*/*")
                                //    .withHeader("Authorization", " Basic " + authHeader("kolov", "029b6bcb9ce57d5f85d8a244179bc209"))
                                .withHeader("Authorization", "Basic a29sb3Y6MDI5YjZiY2I5Y2U1N2Q1Zjg1ZDhhMjQ0MTc5YmMyMDk=")
                        // Basic a29sb3Y6MDI5YjZiY2I5Y2U1N2Q1Zjg1ZDhhMjQ0MTc5YmMyMDk=

                );
    }
}
