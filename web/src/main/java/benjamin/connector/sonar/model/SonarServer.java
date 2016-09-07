package benjamin.connector.sonar.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarServer {
    private String id;
    private String version;
    private String status;

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getStatus() {
        return status;
    }
}
