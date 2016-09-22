package benjamin.connector.jenkins;

import benjamin.connector.common.RestHelper;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JenkinsConnector {

    private static final String PATH_JOBS = "/api/json";


    private RestTemplate restTemplate;
    private RestHelper restHelper;
    private String username;
    private String apiToken;


    private String url;

    private JenkinsConnector(RestHelper restHelper, RestTemplate restTemplate, String url, String username, String apiToken) {
        this.url = url;
        this.username = username;
        this.apiToken = apiToken;
    }


    private RequestEntity makeRequestEntity(final String url) {
        return restHelper.makeRequestEntity(url, username, apiToken);
    }

    public JenkinsJobConfig getJobConfig(String jobname) {

        final RequestEntity re = makeRequestEntity(url + "/job/" + jobname + "/config.xml");
        final ResponseEntity<JenkinsJobConfig> resp = restTemplate.exchange(re,
                JenkinsJobConfig.class);
        return resp.getBody();
    }

    public JenkinsNode listJobs() {
        final RequestEntity<JenkinsNode> re = makeRequestEntity(url + PATH_JOBS);
        final ResponseEntity<JenkinsNode> resp = restTemplate.exchange(re,
                JenkinsNode.class);
        return resp.getBody();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static JenkinsConnector build(RestHelper restHelper, RestTemplate restTemplate, String url, String username, String apiToken) {
        JenkinsConnector connector = new JenkinsConnector(restHelper, restTemplate, url, username, apiToken);
        return connector;
    }
}
