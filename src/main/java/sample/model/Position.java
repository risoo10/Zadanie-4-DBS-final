package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Riso on 4/19/2017.
 */
public class Position {
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

