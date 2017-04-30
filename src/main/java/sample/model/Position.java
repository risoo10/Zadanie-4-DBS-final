package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Entity obsadenie from Entity Model
 * Holds info about position during runtime.
 */
public class Position {

    // Attributes
    private int id;
    private final StringProperty title;

    public Position(int id, String title) {
        this.id = id;
        this.title = new SimpleStringProperty(title);
    }


    @Override
    public String toString() {
        return title.get();
    }

    public int getId() {
        return id;
    }
}

