package benjamin.controller;


import benjamin.connector.sonar.Sonar5Connector;
import benjamin.connector.sonar.SonarConnectorImpl;
import benjamin.connector.sonar.model.Component;
import benjamin.connector.sonar.model.Metric;
import benjamin.connector.sonar.model.Project;
import benjamin.exception.ApplicationException;
import benjamin.model.Settings;
import benjamin.persistence.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
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


    @Value("${ben.core.measures}")
    private String coreMetrics;


    @Autowired
    private BeanFactory beanFactory;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Component> list() {
        return componentsRepository.findAll();
    }


    @RequestMapping(path = "/importFromSonar", method = RequestMethod.POST)
    @ResponseBody
    public String update() {
        final Settings settings = settingsRepositoryExt.getTheSettings();
        if (settings == null) {
            throw new ApplicationException("No Sonar settings");
        }
        SonarConnectorImpl sonarConnector =
                (SonarConnectorImpl) beanFactory.getBean("sonarConnector", settings);
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


    @Autowired
    private TaskExecutor taskExecutor;

    private void updateSonarComponents(SonarConnectorImpl sonarConnector) {
        sonarConnector.init();
        List<Project> projects = sonarConnector.listProjects();

        taskExecutor.execute(() -> projects.stream().forEach(p -> projectsMongoRepository.save(p)));
        taskExecutor.execute(() -> projects.stream().forEach(p -> projectsElasticRepository.save(p)));

        projects.stream()
                .filter(p -> p.getQualifier() == "TRK")
                .forEach(p -> getExtendedProjecInfo(p));
    }

    private void getExtendedProjecInfo(Project p) {

    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Component update(@RequestBody final Component component) {
        return componentsRepository.save(component);
    }


}