package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// Language entity to hold info about Language during runtime
// Maps krajina_povodu entity from Entity Model
public class Language {

    // Attributes
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

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }
}
