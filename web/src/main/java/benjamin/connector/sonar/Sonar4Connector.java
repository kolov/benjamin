package benjamin.connector.sonar;

import benjamin.connector.sonar.model.Project;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class Sonar4Connector {

    private static final String PATH_PROJECTS = "/api/projects";

    private final SonarConnectorImpl sonarConnectorGeneric;

    public Sonar4Connector(SonarConnectorImpl sonarConnectorGeneric) {
        this.sonarConnectorGeneric = sonarConnectorGeneric;
    }

    public List<Project> listProjects() {
        RequestEntity<Project[]> re = sonarConnectorGeneric.makeRequestEntity(PATH_PROJECTS);
        ResponseEntity<Project[]> resp = sonarConnectorGeneric.exchange(re,
                Project[].class);
        return Arrays.asList(resp.getBody());
    }


}
