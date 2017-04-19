package sample;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.model.*;

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

    @FXML
    private ComboBox<Position> positionCombo;

    @FXML
    private Label infoLabel;



    private boolean insertOperation;

    private ObservableList<PersonInMovie> personsInMovies  = FXCollections.observableArrayList();




    @FXML
    void getPreviousScene(ActionEvent event) {

    }

    @FXML
    void lookForLastName(ActionEvent event) throws SQLException {

        // Reset info label to empty string.
        infoLabel.setText("");


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


        // Pair the columns of the table to the actual values of Person
        firstNameCol.setCellValueFactory(celldate -> celldate.getValue().firstNameProperty());
        lastNameCol.setCellValueFactory(celldate -> celldate.getValue().lastNameProperty());
        ageCol.setCellValueFactory(cl -> cl.getValue().ageProperty().asObject());

        // Insert persons found by DB into observable list
        ObservableList<Person> personObserv = FXCollections.observableArrayList();
        for(Person p : persons){
            personObserv.add(p);
        }

        // Display Persons in the table
        personsTable.setItems(personObserv);

        // Inform User if Person doesnt exist.
        if(personObserv.size() > 0) {
        } else {
            infoLabel.setText("Nenasli sa ziadne postavy s hladanim priezviskom.");
        }
    }


    // Pomocou transakcie uloz novy film do databazy.
    // Vyuzijeme transkaciu pomocou ktorej zapiseme vsetky udaje do databazy.
    @FXML
    void saveMovie(ActionEvent event) {


        // Load informations about movie from input fields

        String title = titleField.getText();
        Double rating = Double.parseDouble(ratingField.getText());
        int minutes = Integer.parseInt(minutesField.getText());
        int year = Integer.parseInt(yearField.getText());
        String description = descriptionField.getText();
        int language_id = languageCombo.getValue().getId();
        int genre_id = genreCombo.getValue().getId();




    }

    @FXML
    void initialize() throws SQLException {



        // Get genres and set genre COMBOBOX
        genreCombo.setItems(getGenresDB());
        genreCombo.getSelectionModel().selectFirst();

        // Get languages and set language COMBOBOX
        languageCombo.setItems(getLanguagesDB());
        languageCombo.getSelectionModel().selectFirst();

        // Get available work positions for persons in COMBOBOX
        positionCombo.setItems(getPositionsDB());
        positionCombo.getSelectionModel().selectFirst();


        // Listen to double click on the table row and launch actions
        personsTable.setRowFactory(tv ->{
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() == 2 && ( ! row.isEmpty())){
                    addNewPersonInMovie(row.getItem(), roleField.getText(), positionCombo.getValue());
                }
            });
            return row;
        });


    }



    // Pridaj Osobu do personalneho obsadenia.

    private void addNewPersonInMovie(Person person, String role, Position position){

        // Create new Person In Movie
        PersonInMovie newPersonInMovie = new PersonInMovie(person, role, position.toString());

        // Add to oservable list
        personsInMovies.add(newPersonInMovie);

        // Display actual list of Persons in movie
        personsInMovieList.setText("");
        for(PersonInMovie pim : personsInMovies){
            personsInMovieList.setText(personsInMovieList.getText() +
             pim.getFirstName() + " " + pim.getLastName() + "  (" + pim.getPosition() + ")\n");
        }

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

    // Get List of actually used work positions in DB
    // Return them in the observable list

    private ObservableList<Position> getPositionsDB() throws SQLException {
        ObservableList<Position> observableList = FXCollections.observableArrayList();
        String selectQuery = "SELECT id, nazov FROM obsadenie;\n";
        List<Position> values = new DBConnector().select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Position(
                        rs.getInt("id"),
                        rs.getString("nazov")
                );
            }
        });

        for(Position g : values){
            observableList.add(g);
        }

        return observableList;
    }


}
