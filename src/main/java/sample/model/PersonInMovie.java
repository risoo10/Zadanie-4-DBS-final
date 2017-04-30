package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Entity osoba_vofilme from Enity Model
 * Holds info about Person In Movie during runtime
 */
public class PersonInMovie {

    // Attributes
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private int person_id;
    private final StringProperty movieName;
    private final StringProperty position;
    private int position_id;

    public PersonInMovie(int id, String firstName, String lastName, String movieName, String position, int person_id, int position_id) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.movieName = new SimpleStringProperty(movieName);
        this.position = new SimpleStringProperty(position);
        this.position_id = position_id;
        this.person_id = person_id;
    }

    public PersonInMovie( Person person,  String movieName, String position, int person_id, int position_id) {
        this.id = null;
        this.firstName = new SimpleStringProperty(person.getFirstName());
        this.lastName = new SimpleStringProperty(person.getLastName());
        this.movieName = new SimpleStringProperty(movieName);
        this.position = new SimpleStringProperty(position);
        this.position_id = position_id;
        this.person_id = person_id;
    }


    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMovieName() {
        return movieName.get();
    }

    public StringProperty movieNameProperty() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName.set(movieName);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public int getPerson_id() {
        return person_id;
    }

    public int getPosition_id() {
        return position_id;
    }
}
