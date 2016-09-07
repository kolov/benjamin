package benjamin.model;


import benjamin.connector.jenkins.JenkinsSettings;
import benjamin.connector.sonar.SonarSettings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

public class Settings implements SonarSettings, JenkinsSettings {


    @Id
    @JsonIgnore
    private String id;


    private String sonarUrl;
    private String sonarUser;
    private String sonarPassword;

    private String jenkinsUrl;
    private String jenkinsUser;
    private String jenkinsApiToken;

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public void setJenkinsUrl(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }

    public String getJenkinsUser() {
        return jenkinsUser;
    }

    public void setJenkinsUser(String jenkinsUser) {
        this.jenkinsUser = jenkinsUser;
    }

    public String getJenkinsApiToken() {
        return jenkinsApiToken;
    }

    public void setJenkinsApiToken(String jenkinsApiToken) {
        this.jenkinsApiToken = jenkinsApiToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSonarUrl() {
        return sonarUrl;
    }

    public void setSonarUrl(String sonarUrl) {
        this.sonarUrl = sonarUrl;
    }

    public String getSonarUser() {
        return sonarUser;
    }

    public void setSonarUser(String sonarUser) {
        this.sonarUser = sonarUser;
    }

    public String getSonarPassword() {
        return sonarPassword;
    }

    public void setSonarPassword(String sonarPassword) {
        this.sonarPassword = sonarPassword;
    }
}
