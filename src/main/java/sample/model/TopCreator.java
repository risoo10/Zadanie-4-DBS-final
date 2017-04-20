package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Riso on 4/19/2017.
 */
public class TopCreator {

    private final IntegerProperty filmCount;
    private final Person person;

    public TopCreator(int filmCount, Person person) {
        this.filmCount = new SimpleIntegerProperty(filmCount);
        this.person = person;
    }

    public int getFilmCount() {
        return filmCount.get();
    }

    public IntegerProperty filmCountProperty() {
        return filmCount;
    }

    public StringProperty firstNameProperty() {
        return person.firstNameProperty();
    }

    public String getFirstName(){
        return person.getFirstName();
    }

    public StringProperty lastNameProperty() {
        return person.lastNameProperty();
    }

    public String getLastName(){
        return person.getLastName();
    }
}
