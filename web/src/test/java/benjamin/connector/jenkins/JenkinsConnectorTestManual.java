package benjamin.connector.jenkins;


import benjamin.ApplicationConfiguration;
import benjamin.connector.LoggingRequestInterceptor;
import benjamin.connector.common.RestHelper;
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
 * Run manually to connect to Jenkins and retrieve and output responses.
 * <p>
 * You need to define a few JENKINS_XXX properties in order to run it.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"/private.properties", "/converters-tests.properties"})
@ContextConfiguration(classes = {ApplicationConfiguration.class, JenkinsConnectorFactory.class, RestHelper.class})
public class JenkinsConnectorTestManual {

    @Autowired
    private JenkinsConnectorFactory jenkinsConnectorFactory;

    @Autowired
    private RestHelper restHelper;

    @Value("${JENKINS_TOKEN}")
    private String jenkinsApiToken;

    @Value("${JENKINS_URL}")
    private String jenkinsUrl;

    @Value("${JENKINS_USER}")
    private String jenkinsUser;

    @Before
    public void init() {

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new LoggingRequestInterceptor());
        restHelper.setInterceptors(interceptors);
    }

    @Test
    public void testGetJobs() {
        final JenkinsConnector jenkinsConnector =
                jenkinsConnectorFactory.createJenkinsConnector(
                        jenkinsUrl, jenkinsUser, jenkinsApiToken);
        jenkinsConnector.listJobs();
    }

    @Test
    public void getJobConfig() {
        final JenkinsConnector jenkinsConnector = jenkinsConnectorFactory.createJenkinsConnector(
                jenkinsUrl, jenkinsUser, jenkinsApiToken);
        jenkinsConnector.getJobConfig("benjamin-commit");

    }

}