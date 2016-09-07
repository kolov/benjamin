package benjamin.sonar;

import benjamin.connector.common.RestHelper;
import benjamin.connector.sonar.SonarConnector;
import benjamin.connector.sonar.model.Component;
import benjamin.connector.sonar.model.Metric;
import benjamin.connector.sonar.model.Project;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;


public class SonarConnectorTestManual {


    @Test
    public void listProjects() throws Exception {
        SonarConnector sonarConnector = createConnector();


        Project[] projects = sonarConnector.listProjects();
        Assert.assertTrue(projects.length > 0);
        System.out.println("Found " + projects.length);
    }

    @Test
    public void listComponents() throws Exception {
        SonarConnector sonarConnector = createConnector();

        Project[] projects = sonarConnector.listProjects();
        List<Component> components = sonarConnector.queryComponentsOfProject(projects[0].getKey(), "violations");
        Assert.assertTrue(components.size() > 0);
        System.out.println("Found " + components.size());
    }

    @Test
    public void listMetrics() throws Exception {
        SonarConnector sonarConnector = createConnector();


        List<Metric> metrics = sonarConnector.listMetrics();
        Assert.assertTrue(metrics.size() > 0);
        System.out.println("Found " + metrics.size());
    }

    private SonarConnector createConnector() {
        RestHelper re = createRestHelper();
        return new SonarConnector(re, "http://ben-sonar.akolov.com:9000", "assen", "jeanpaul");
    }


    private RestHelper createRestHelper() {
        RestHelper re = new RestHelper();
        List converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        ReflectionTestUtils.setField(re, "messageConverters", converters);
        return re;
    }


}