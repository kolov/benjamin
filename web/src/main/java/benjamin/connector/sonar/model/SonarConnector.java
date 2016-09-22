package benjamin.connector.sonar.model;


import java.util.List;

public interface SonarConnector {
    List<Project> listProjects();
    void setLogging(boolean b);
}
