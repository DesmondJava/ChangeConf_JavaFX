package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Администратор on 13.07.2015.
 */
public class Metals {

    private final StringProperty title;
    private final DoubleProperty price;

    public Metals() {
        this(null, null);
    }


    public Metals(String title, Double price) {
        this.title = new SimpleStringProperty(title);
        this.price = new SimpleDoubleProperty(price);;
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

    public Double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }
}
