package benjamin.connector.sonar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metric {

    private String id;
    private String key;
    private String type;
    private String name;
    private String description;
    private String domain;
    private Integer direction;
    private boolean qualitative;
    private boolean hidden;
    private boolean custom;

    public String getId() {
        return id;
    }

    @Id
    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDomain() {
        return domain;
    }

    public Integer getDirection() {
        return direction;
    }

    public boolean isQualitative() {
        return qualitative;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isCustom() {
        return custom;
    }
}


