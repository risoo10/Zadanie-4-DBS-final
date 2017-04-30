package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Doesn't map entity from Entity Model
 * Holds info about Person and sum of movies where the Person been involved.
 */
public class TopCreator {

    // Attributes
    private final IntegerProperty movieCount;
    private final Person person;

    public TopCreator(int movieCount, Person person) {
        this.movieCount = new SimpleIntegerProperty(movieCount);
        this.person = person;
    }

    public int getmovieCount() {
        return movieCount.get();
    }

    public IntegerProperty movieCountProperty() {
        return movieCount;
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
