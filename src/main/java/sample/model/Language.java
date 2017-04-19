package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Riso on 4/19/2017.
 */
public class Language {
    private final IntegerProperty id;
    private final StringProperty name;

    public Language(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
