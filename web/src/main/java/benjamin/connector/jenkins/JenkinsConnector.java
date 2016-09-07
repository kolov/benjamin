package benjamin.connector.jenkins;

import benjamin.connector.common.RestHelper;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class JenkinsConnector {

    private static final String PATH_JOBS = "/api/json";

    private final RestHelper restHelper;

    private String username;
    private String apiToken;
    private String url;

    public JenkinsConnector(RestHelper restHelper, String url, String username, String apiToken) {
        this.restHelper = restHelper;
        this.url = url;
        this.username = username;
        this.apiToken = apiToken;
    }

    public JenkinsConnector(RestHelper restHelper, JenkinsSettings jenkinsSettings) {
        this.restHelper = restHelper;
        this.url = jenkinsSettings.getJenkinsUrl();
        this.username = jenkinsSettings.getJenkinsUser();
        this.apiToken = jenkinsSettings.getJenkinsApiToken();
    }


    private RequestEntity makeRequestEntity(final String url) {
        return restHelper.makeRequestEntity(url, username, apiToken);
    }

    public JenkinsJobConfig getJobConfig(String jobname) {

        final RequestEntity re = makeRequestEntity(url + "/job/" + jobname + "/config.xml");
        final ResponseEntity<JenkinsJobConfig> resp = restHelper.getRestTemplate().exchange(re,
            JenkinsJobConfig.class);
        return resp.getBody();
    }

    public JenkinsNode listJobs() {
        final RequestEntity<JenkinsNode> re = makeRequestEntity(url + PATH_JOBS);
        final ResponseEntity<JenkinsNode> resp = restHelper.getRestTemplate().exchange(re,
            JenkinsNode.class);
        return resp.getBody();
    }

}
