package benjamin.controller;


import benjamin.connector.sonar.SonarConnector;
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


    private SonarConnector createSonarConnector() {
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
        SonarConnector sonarConnector = createSonarConnector();


        updateSonarComponents(sonarConnector);
        updateMetrics(sonarConnector);

        return "OK";
    }

    private void updateMetrics(SonarConnector sonarConnector) {
        List<Metric> metrics = sonarConnector.listMetrics();
        for (Metric metric : metrics) {
            metricsRepository.save(metric);
        }
    }


    private void updateSonarComponents(SonarConnector sonarConnector) {
        Project[] projects = sonarConnector.listProjects();
        for (Project project : projects) {
            projectsRepository.save(project);
            List<Component> comps = sonarConnector.queryComponentsOfProject(project.getKey(), coreMetrics);
            comps.forEach(c -> componentsRepository.save(c));
        }
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Component update(@RequestBody final Component component) {
        return componentsRepository.save(component);
    }


}