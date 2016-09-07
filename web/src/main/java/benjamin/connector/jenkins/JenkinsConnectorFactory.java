package benjamin.connector.jenkins;

import benjamin.connector.common.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JenkinsConnectorFactory {

    @Autowired
    private RestHelper restHelper;

    public JenkinsConnector createJenkinsConnector(JenkinsSettings jenkinsSettings) {
        return new JenkinsConnector(restHelper, jenkinsSettings);
    }


    public JenkinsConnector createJenkinsConnector(String url, String user, String pw) {
        return new JenkinsConnector(restHelper, url, user, pw);
    }

    public RestHelper getRestHelper() {
        return restHelper;
    }
}
