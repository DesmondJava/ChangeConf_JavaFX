package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfValue {

    private final StringProperty title;
    private final StringProperty value;
    private final StringProperty sort;

    public ConfValue() {
        this(null, null, null);
    }


    public ConfValue(String title, String value, String sort) {
        this.title = new SimpleStringProperty(title);
        this.value = new SimpleStringProperty(value);;
        this.sort = new SimpleStringProperty(sort);;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setPrice(String value) {
        this.value.set(value);
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getSort() {
        return sort.get();
    }

    public StringProperty sortProperty() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort.set(sort);
    }
}
