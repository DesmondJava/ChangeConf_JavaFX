package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfValue {

    private final StringProperty title;
    private final StringProperty value;

    public ConfValue() {
        this(null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfValue confValue = (ConfValue) o;

        if (title != null ? !title.equals(confValue.title) : confValue.title != null) return false;
        return !(value != null ? !value.equals(confValue.value) : confValue.value != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public ConfValue(String title, String value) {
        this.title = new SimpleStringProperty(title);
        this.value = new SimpleStringProperty(value);;
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
}
