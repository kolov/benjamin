package benjamin.controller;


import benjamin.connector.sonar.Sonar5Connector;
import benjamin.connector.sonar.SonarConnectorFactory;
import benjamin.connector.sonar.model.Component;
import benjamin.connector.sonar.model.Metric;
import benjamin.connector.sonar.model.Project;
import benjamin.exception.ApplicationException;
import benjamin.model.Settings;
import benjamin.persistence.ComponentsRepository;
import benjamin.persistence.MetricsRepository;
import benjamin.persistence.ProjectsRepository;
import benjamin.persistence.SettingsRepositoryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/v1/components")
public class ComponentsController {

    @Autowired
    private ComponentsRepository componentsRepository;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private MetricsRepository metricsRepository;

    @Autowired
    private SettingsRepositoryExt settingsRepositoryExt;

    @Autowired
    private SonarConnectorFactory sonarConnectorFactory;

    @Value("${ben.core.measures}")
    private String coreMetrics;


    private Sonar5Connector createSonarConnector() {
        final Settings settings = settingsRepositoryExt.getTheSettings();
        if (settings == null) {
            throw new ApplicationException("No Sonar settings");
        }

        return sonarConnectorFactory.createSonarConnector(settings);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Component> list() {
        return componentsRepository.findAll();
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String update() {
        Sonar5Connector sonar5Connector = createSonarConnector();


        updateSonarComponents(sonar5Connector);
        updateMetrics(sonar5Connector);

        return "OK";
    }

    private void updateMetrics(Sonar5Connector sonar5Connector) {
        List<Metric> metrics = sonar5Connector.listMetrics();
        for (Metric metric : metrics) {
            metricsRepository.save(metric);
        }
    }


    private void updateSonarComponents(Sonar5Connector sonar5Connector) {
        Project[] projects = sonar5Connector.listProjects();
        for (Project project : projects) {
            projectsRepository.save(project);
            List<Component> comps = sonar5Connector.queryComponentsOfProject(project.getKey(), coreMetrics);
            comps.forEach(c -> componentsRepository.save(c));
        }
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Component update(@RequestBody final Component component) {
        return componentsRepository.save(component);
    }


}