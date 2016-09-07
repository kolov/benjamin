package benjamin.connector.sonar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.elasticsearch.annotations.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "sonar")
public class Project {

    private String id;
    private String key;
    private String name;
    private String sc;
    private String qualifier;


    public String getId() {
        return id;
    }

    @JsonProperty("sc")
    public String getSc() {
        return sc;
    }

    @JsonProperty("qu")
    public String getQualifier() {
        return qualifier;
    }

    @JsonProperty("k")
    public String getKey() {
        return key;
    }

    @JsonProperty("nm")
    public String getName() {
        return name;
    }

}
