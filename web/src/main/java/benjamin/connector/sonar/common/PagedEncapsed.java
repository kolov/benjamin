package benjamin.connector.sonar.common;


import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PagedEncapsed<T> implements Paged<T> {

    protected static class Paging

    {

        protected int total;
        protected int page;
        protected int pageSize;


        @JsonProperty("total")
        public int getTotal() {
            return total;
        }

        @JsonProperty("pageIndex")
        public int getPage() {
            return page;
        }


        @JsonProperty("pageSize")
        public int getPageSize() {
            return pageSize;
        }
    }

    private Paging paging;

    public Paging getPaging() {
        return paging;
    }

    @Override
    public int getTotal() {
        return paging.getTotal();
    }

    @Override
    public int getPage() {
        return paging.getPage();
    }

    @Override
    public int getPageSize() {
        return paging.getPageSize();
    }
}
