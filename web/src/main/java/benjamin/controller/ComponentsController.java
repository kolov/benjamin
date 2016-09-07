package benjamin.controller;


import benjamin.connector.sonar.Sonar5Connector;
import benjamin.connector.sonar.SonarConnectorFactory;
import benjamin.connector.sonar.SonarConnectorGeneric;
import benjamin.connector.sonar.model.Component;
import benjamin.connector.sonar.model.Metric;
import benjamin.connector.sonar.model.Project;
import benjamin.exception.ApplicationException;
import benjamin.model.Settings;
import benjamin.persistence.*;
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
    private ProjectsMongoRepository projectsMongoRepository;

    @Autowired
    private ProjectsElasticRepository projectsElasticRepository;

    @Autowired
    private MetricsRepository metricsRepository;

    @Autowired
    private SettingsRepositoryExt settingsRepositoryExt;

    @Autowired
    private SonarConnectorFactory sonarConnectorFactory;

    @Value("${ben.core.measures}")
    private String coreMetrics;


    private SonarConnectorGeneric createSonarConnector() {
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


    @RequestMapping(path = "/importFromSonar", method = RequestMethod.POST)
    @ResponseBody
    public String update() {
        SonarConnectorGeneric sonarConnector = createSonarConnector();
        updateSonarComponents(sonarConnector);
        // updateMetrics(sonarConnector);

        return "OK";
    }

    private void updateMetrics(Sonar5Connector sonar5Connector) {
        List<Metric> metrics = sonar5Connector.listMetrics();
        for (Metric metric : metrics) {
            metricsRepository.save(metric);
        }
    }


    private void updateSonarComponents(SonarConnectorGeneric sonarConnector) {
        sonarConnector.init();
        List<Project> projects = sonarConnector.listProjects();
        // TODO: parallel
        projects.stream().forEach(p -> projectsMongoRepository.save(p));
        projects.stream().forEach(p -> projectsElasticRepository.save(p));

        //List<Component> comps = sonar5Connector.queryComponentsOfProject(project.getKey(), coreMetrics);
        //comps.forEach(c -> componentsRepository.save(c));

    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Component update(@RequestBody final Component component) {
        return componentsRepository.save(component);
    }


}