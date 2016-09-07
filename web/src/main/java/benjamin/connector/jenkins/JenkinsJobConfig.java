package benjamin.connector.jenkins;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "maven2-moduleset")
public class JenkinsJobConfig {

    private Scm scm;
    private UserRemoteConfigs userRemoteConfigs;


    @XmlRootElement(name = "scm")
    public static class Scm {

        private String clazz;

        private UserRemoteConfigs userRemoteConfigs;

        @XmlElement
        public UserRemoteConfigs getUserRemoteConfigs() {
            return userRemoteConfigs;
        }

        public void setUserRemoteConfigs(UserRemoteConfigs userRemoteConfigs) {
            this.userRemoteConfigs = userRemoteConfigs;
        }

        @XmlAttribute(name = "class")
        public String getClazz() {
            return clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }
    }

    @XmlRootElement(name = "userRemoteConfigs")
    public static class UserRemoteConfigs {

        private List<UserRemoteConfig> userRemoteConfigList;

        @XmlElement(name = "hudson.plugins.git.UserRemoteConfig")
        public List<UserRemoteConfig> getUserRemoteConfigList() {
            return userRemoteConfigList;
        }

        public void setUserRemoteConfigList(List<UserRemoteConfig> userRemoteConfigList) {
            this.userRemoteConfigList = userRemoteConfigList;
        }
    }

    public static class UserRemoteConfig {

        private String url;

        @XmlElement
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


    public UserRemoteConfigs getUserRemoteConfigs() {
        return userRemoteConfigs;
    }

    public void setUserRemoteConfigs(UserRemoteConfigs userRemoteConfigs) {
        this.userRemoteConfigs = userRemoteConfigs;
    }

    public Scm getScm() {
        return scm;
    }

    public void setScm(Scm scm) {
        this.scm = scm;
    }
}
