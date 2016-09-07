package benjamin.connector.sonar.model;


import benjamin.connector.sonar.common.PagedEmbedded;

public class PagedMetrics5 extends PagedEmbedded<Metric> {

    public Metric[] getMetrics() {
        return metrics;
    }

    private Metric[] metrics;

    public Metric[] getItems() {
        return metrics;
    }

}
