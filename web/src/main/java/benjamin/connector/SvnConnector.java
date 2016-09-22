package benjamin.connector;

import benjamin.connector.common.RestHelper;
import benjamin.connector.jenkins.JenkinsSettings;
import org.springframework.http.RequestEntity;


public class SvnConnector {


    private final RestHelper restHelper;

    private String username;
    private String apiToken;
    private String url;

    public SvnConnector(RestHelper restHelper, String url, String username, String apiToken) {
        this.restHelper = restHelper;
        this.url = url;
        this.username = username;
        this.apiToken = apiToken;
    }

    public SvnConnector(RestHelper restHelper, JenkinsSettings jenkinsSettings) {
        this.restHelper = restHelper;
        this.url = jenkinsSettings.getJenkinsUrl();
        this.username = jenkinsSettings.getJenkinsUser();
        this.apiToken = jenkinsSettings.getJenkinsApiToken();
    }


    private RequestEntity makeRequestEntity(final String url) {
        return restHelper.makeRequestEntity(url, username, apiToken);
    }



}
