package benjamin.connector.sonar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    private String key;
    private String name;

    @JsonProperty("sc")
    public String getSc() {
        return sc;
    }

    @JsonProperty("qu")
    public String getQualifier() {
        return qualifier;
    }

    private String sc;
    private String qualifier;

    @JsonProperty("k")
    public String getKey() {
        return key;
    }

    @JsonProperty("nm")
    public String getName() {
        return name;
    }

}
