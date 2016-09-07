package benjamin.connector.sonar;

import benjamin.connector.common.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SonarConnectorFactory {

    @Autowired
    private RestHelper restHelper;

    public SonarConnector createSonarConnector(SonarSettings sonarSettings) {
        return new SonarConnector(restHelper, sonarSettings);
    }

    public SonarConnector createSonarConnector(String url, String username, String password) {
        return new SonarConnector(restHelper, url, username, password);
    }

}
