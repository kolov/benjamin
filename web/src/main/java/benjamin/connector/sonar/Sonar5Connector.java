package benjamin.connector.sonar;

import benjamin.connector.sonar.common.Paged;
import benjamin.connector.sonar.model.*;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sonar5Connector {

    private static final String PATH_PROJECTS = "/api/projects";
    private static final String PATH_METRIX_SEARCH = "/api/metrics/search";
    private static final String PATH_MEASURES_TREE = "/api/measures/component_tree";

    private final SonarConnectorGeneric sonarConnectorGeneric;

    public Sonar5Connector(SonarConnectorGeneric sonarConnectorGeneric) {
        this.sonarConnectorGeneric = sonarConnectorGeneric;
    }

    public List<Component> queryComponentsOfProject(String projectKey, String coreMetrics) {

        String url = PATH_MEASURES_TREE + "?metricKeys=" + coreMetrics
                + "&baseComponentKey=" + projectKey
                + "&qualifiers=TRK,BRC";
        return queryPaged(url, PagedComponents5.class);
    }

    public List<Project> listProjects() {
        RequestEntity<Project[]> re = sonarConnectorGeneric.makeRequestEntity(PATH_PROJECTS);
        ResponseEntity<Project[]> resp = sonarConnectorGeneric.exchange(re,
                Project[].class);
        return Arrays.asList(resp.getBody());
    }

    private <T, PT extends Paged<T>> List<T> queryPaged(String url, Class<PT> pagedClass) {

        final List<T> result = new ArrayList<>();
        boolean finished = false;
        for (int p = 1; !finished; p++) {
            final String urlFull = url
                    + (url.contains("?") ? "&" : "?")
                    + "p=" + p + "&ps=100";
            final RequestEntity<PT> re = sonarConnectorGeneric.makeRequestEntity(urlFull);
            final ResponseEntity<PT> resp = sonarConnectorGeneric.exchange(re,
                    pagedClass);
            final PT pm = resp.getBody();
            result.addAll(Arrays.asList(pm.getItems()));
            finished = pm.getTotal() < p * 100;
        }
        return result;
    }

    public List<Metric> listMetrics() {
        return queryPaged(PATH_METRIX_SEARCH, PagedMetrics5.class);
    }

}
