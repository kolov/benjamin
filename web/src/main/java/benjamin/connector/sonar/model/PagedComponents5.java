package benjamin.connector.sonar.model;


import benjamin.connector.sonar.common.PagedEncapsed;

public class PagedComponents5 extends PagedEncapsed<Component> {

    private Component baseComponent;
    private Component[] components;

    public Component[] getComponents() {
        return components;
    }

    public Component getBaseComponent() {
        return baseComponent;

    }

    public Component[] getItems() {
        return components;
    }

}
