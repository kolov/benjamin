package benjamin.connector.sonar;

import benjamin.connector.common.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SonarConnectorFactory {

    @Autowired
    private RestHelper restHelper;

    public SonarConnectorGeneric createSonarConnector(SonarSettings sonarSettings) {
        return new SonarConnectorGeneric(restHelper, sonarSettings);
    }

    public SonarConnectorGeneric createSonarConnector(String url, String username, String password) {
        return new SonarConnectorGeneric(restHelper, url, username, password);
    }

    public Sonar5Connector createSonar5Connector(String url, String username, String password) {
        return new Sonar5Connector(createSonarConnector(url, username, password));
    }

}
