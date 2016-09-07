package benjamin.connector.jenkins;

import java.util.List;

/**
 * Respons to http://jenkins.akolov.com/api/json
 */
public class JenkinsNode {
    private List<JenkinsJob> jobs;

    public List<JenkinsJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<JenkinsJob> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "JenkinsNode{" +
            "jobs=" + jobs +
            '}';
    }
}
