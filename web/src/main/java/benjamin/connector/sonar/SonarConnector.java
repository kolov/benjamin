package benjamin.connector.sonar;

import benjamin.connector.common.RestHelper;
import benjamin.connector.sonar.common.Paged;
import benjamin.connector.sonar.model.Component;
import benjamin.connector.sonar.model.Metric;
import benjamin.connector.sonar.model.PagedComponents;
import benjamin.connector.sonar.model.PagedMetrics;
import benjamin.connector.sonar.model.Project;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SonarConnector {

    private static final String PATH_PROJECTS = "/api/projects";
    private static final String PATH_METRIX_SEARCH = "/api/metrics/search";
    private static final String PATH_MEASURES_TREE = "/api/measures/component_tree";

    private final RestHelper restHelper;

    private String username;
    private String password;
    private String url;


    public SonarConnector(RestHelper restHelper, String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.restHelper = restHelper;
    }

    public SonarConnector(RestHelper restHelper, SonarSettings sonarsettings) {
        this(restHelper,
            sonarsettings.getSonarUrl(),
            sonarsettings.getSonarUser(),
            sonarsettings.getSonarPassword());
    }


    private RequestEntity makeRequestEntity(final String path) {
        return restHelper.makeRequestEntity(url, path, username, password);
    }


    public List<Component> queryComponentsOfProject(String projectKey, String coreMetrics) {

        String url = PATH_MEASURES_TREE + "?metricKeys=" + coreMetrics
            + "&baseComponentKey=" + projectKey
            + "&qualifiers=TRK,BRC";
        return queryPaged(url, PagedComponents.class);
    }

    public Project[] listProjects() {
        RequestEntity<Project[]> re = makeRequestEntity(PATH_PROJECTS);
        ResponseEntity<Project[]> resp = restHelper.getRestTemplate().exchange(re,
            Project[].class);
        return resp.getBody();
    }

    private <T, PT extends Paged<T>> List<T> queryPaged(String url, Class<PT> pagedClass) {

        final List<T> result = new ArrayList<>();
        boolean finished = false;
        for (int p = 1; !finished; p++) {
            final String urlFull = url
                + (url.contains("?") ? "&" : "?")
                + "p=" + p + "&ps=100";
            final RequestEntity<PT> re = makeRequestEntity(urlFull);
            final ResponseEntity<PT> resp = restHelper.getRestTemplate().exchange(re,
                pagedClass);
            final PT pm = resp.getBody();
            result.addAll(Arrays.asList(pm.getItems()));
            finished = pm.getTotal() < p * 100;
        }
        return result;
    }

    public List<Metric> listMetrics() {
        return queryPaged(PATH_METRIX_SEARCH, PagedMetrics.class);
    }

}
