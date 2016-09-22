package benjamin.connector.sonar;


import benjamin.ApplicationConfiguration;
import benjamin.connector.common.RestTemplateConfig;
import benjamin.connector.sonar.model.SonarConnector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Run manually to connect to Sonarqube and retrieve and output responses.
 * <p>
 * You need to define a few SONAR_XXX properties in order to run it
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"/converters-tests.properties", "/private.properties"})
@ContextConfiguration(classes = {ApplicationConfiguration.class, RestTemplateConfig.class})
public class SonarConnectorTestManual {


    @Value("${SONAR_PASSWORD}")
    private String sonarPassword;

    @Value("${SONAR_URL}")
    private String sonarUrl;

    @Value("${SONAR_USER}")
    private String sonarUser;

    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void testGetJobs() {
        SonarConnector sonarConnector =
                (SonarConnector) beanFactory.getBean("sonarConnector", sonarUrl, sonarUser, sonarPassword);



        sonarConnector.setLogging(true);
        sonarConnector.listProjects();
    }

}