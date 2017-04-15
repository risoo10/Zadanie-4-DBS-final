package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Riso on 4/15/2017.
 */
public class PersonInMovie {
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty movieName;
    private final StringProperty position;

    public PersonInMovie(int id, String firstName, String lastName, String movieName, String position) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.movieName = new SimpleStringProperty(movieName);
        this.position = new SimpleStringProperty(position);
    }


}
