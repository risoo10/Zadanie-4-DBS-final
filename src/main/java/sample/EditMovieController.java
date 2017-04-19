package sample;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.Genre;
import sample.model.Language;
import sample.model.Person;
import sample.model.PersonInMovie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Riso on 4/19/2017.
 */
public class EditMovieController {

    @FXML
    private TextField personLastNameField;

    @FXML
    private TableColumn<Person, Integer> ageCol;

    @FXML
    private TextField yearField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField roleField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TableView<Person> personsTable;

    @FXML
    private TableColumn<Person, String> firstNameCol;

    @FXML
    private TableColumn<Person, String> lastNameCol;

    @FXML
    private Label personsInMovieList;

    @FXML
    private ComboBox<Language> languageCombo;

    @FXML
    private TextField minutesField;

    @FXML
    private TextField ratingField;

    @FXML
    private ComboBox<Genre> genreCombo;



    private boolean insertOperation;

    private List<PersonInMovie> personsInMovies  = new LinkedList<>();




    @FXML
    void getPreviousScene(ActionEvent event) {

    }

    @FXML
    void lookForLastName(ActionEvent event) throws SQLException {

        String lastName = personLastNameField.getText();
        // DB is searching in uppercase if user input lowercase last name
        String upperLastName = lastName.toUpperCase();

        DBConnector dbc = new DBConnector();

        String selectQuery = "SELECT f.id, f.meno, f.priezvisko, EXTRACT(YEARS FROM age(current_date, datum_narodenia)) AS vek FROM osoba f\n" +
                "WHERE upper(f.priezvisko) LIKE '%"+ upperLastName +"%'\n" +
                ";\n";
        List<Person> persons =  dbc.select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Person(
                        rs.getInt("id"),
                        rs.getString("meno"),
                        rs.getString("priezvisko"),
                        rs.getInt("vek")
                );
            }
        });

        firstNameCol.setCellValueFactory(celldate -> celldate.getValue().firstNameProperty());
        lastNameCol.setCellValueFactory(celldate -> celldate.getValue().lastNameProperty());
        ageCol.setCellValueFactory(cl -> cl.getValue().ageProperty().asObject());

        ObservableList<Person> personObserv = FXCollections.observableArrayList();
        for(Person p : persons){
            personObserv.add(p);
        }

        personsTable.setItems(personObserv);
    }

    @FXML
    void saveMovie(ActionEvent event) {

        String title = titleField.getText();
        Double rating = Double.parseDouble(ratingField.getText());
        int minutes = Integer.parseInt(minutesField.getText());
        int year = Integer.parseInt(yearField.getText());
        String description = descriptionField.getText();


    }

    @FXML
    void initialize() throws SQLException {

        // Get genres and set genre COMBOBOX
        genreCombo.setItems(getGenresDB());

        // Get languages and set language COMBOBOX
        languageCombo.setItems(getLanguagesDB());

    }


    // Get List of actually used Genres in DB
    // Return them in the observable list

    private ObservableList<Genre> getGenresDB() throws SQLException {
        ObservableList<Genre> genresObser = FXCollections.observableArrayList();
        String selectQuery = "SELECT id, nazov FROM zaner;\n";
        List<Genre> genres = new DBConnector().select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Genre(
                        rs.getInt("id"),
                        rs.getString("nazov")
                );
            }
        });

        for(Genre g : genres){
            genresObser.add(g);
        }

        return genresObser;
    }


    private ObservableList<Language> getLanguagesDB() throws SQLException {

        // Get languages
        ObservableList<Language> languagesObs = FXCollections.observableArrayList();
        String selectQuery = "SELECT id, skratka FROM krajina_povodu;\n";
        List<Language> languages = new DBConnector().select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Language(
                        rs.getInt("id"),
                        rs.getString("skratka")
                );
            }
        });

        for(Language g : languages){
            languagesObs.add(g);
        }

        return languagesObs;

    }


}
