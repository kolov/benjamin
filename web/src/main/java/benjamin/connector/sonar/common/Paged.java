package benjamin.connector.sonar.common;


public interface Paged<T> {
    int getTotal();

    int getPage();

    int getPageSize();

    T[] getItems();
}
