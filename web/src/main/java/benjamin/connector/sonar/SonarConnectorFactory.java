package benjamin.connector.sonar;

import benjamin.connector.common.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SonarConnectorFactory {

    @Autowired
    private RestHelper restHelper;

    public Sonar5Connector createSonarConnector(SonarSettings sonarSettings) {
        return new Sonar5Connector(restHelper, sonarSettings);
    }

    public Sonar5Connector createSonarConnector(String url, String username, String password) {
        return new Sonar5Connector(restHelper, url, username, password);
    }

}
