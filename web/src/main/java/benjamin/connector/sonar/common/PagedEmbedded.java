package benjamin.connector.sonar.common;


import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PagedEmbedded<T> implements Paged<T> {
    protected int total;
    protected int page;
    protected int pageSize;


    @JsonProperty("total")
    public int getTotal() {
        return total;
    }

    @JsonProperty("p")
    public int getPage() {
        return page;
    }


    @JsonProperty("ps")
    public int getPageSize() {
        return pageSize;
    }

}
