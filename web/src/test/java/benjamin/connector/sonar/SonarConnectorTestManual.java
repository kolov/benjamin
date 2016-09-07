package benjamin.connector.sonar;


import benjamin.ApplicationConfiguration;
import benjamin.connector.LoggingRequestInterceptor;
import benjamin.connector.common.RestHelper;
import benjamin.connector.jenkins.JenkinsConnectorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Run manually to connect to Sonarqube and retrieve and output responses.
 * <p>
 * You need to define a few SONAR_XXX properties in order to run it
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"/converters-tests.properties", "/private.properties"})
@ContextConfiguration(classes = {ApplicationConfiguration.class, JenkinsConnectorFactory.class, RestHelper.class})
public class SonarConnectorTestManual {

    @Autowired
    private SonarConnectorFactory sonarConnectorFactory;

    @Autowired
    private RestHelper restHelper;

    @Value("${SONAR_PASSWORD}")
    private String sonarPassword;

    @Value("${SONAR_URL}")
    private String sonarUrl;

    @Value("${SONAR_USER}")
    private String sonarUser;

    @Before
    public void init() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new LoggingRequestInterceptor());
        restHelper.setInterceptors(interceptors);
    }

    @Test
    public void testGetJobs() {
        final Sonar5Connector sonar5Connector
                = sonarConnectorFactory.createSonarConnector(sonarUrl, sonarUser, sonarPassword);
        sonar5Connector.listProjects();
    }

}