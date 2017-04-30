package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.dbmanagment.DBConnector;
import sample.dbmanagment.Parser;
import sample.model.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Riso on 4/19/2017.
 */
public class NewMovieController {


    // Input controls for user to insert information
    // Fields for basic information abou movie
    @FXML
    protected TextField yearField;
    @FXML
    protected TextField titleField;
    @FXML
    protected TextArea descriptionField;
    @FXML
    protected TextField minutesField;
    @FXML
    protected TextField ratingField;
    @FXML
    protected ComboBox<Genre> genreCombo;
    @FXML
    protected ComboBox<Language> languageCombo;
    @FXML
    protected DatePicker premieraDatePicker;


    // Controls for searching persons
    @FXML
    protected TextField personLastNameField;
    @FXML
    protected TextField roleField;
    @FXML
    protected ComboBox<Position> positionCombo;

    // Table to show results of the search
    @FXML
    protected TableView<Person> personsTable;
    @FXML
    protected TableColumn<Person, Integer> ageCol;
    @FXML
    protected TableColumn<Person, String> firstNameCol;
    @FXML
    protected TableColumn<Person, String> lastNameCol;

    @FXML
    protected Label infoLabel;

    // Table to the display movie creator actualy attached to the movie
    @FXML
    protected TableView<PersonInMovie> personsInMovieTable;
    @FXML
    protected TableColumn<PersonInMovie,String> PIM_firstNameCol;
    @FXML
    protected TableColumn<PersonInMovie,String> PIM_lastNameCol;
    @FXML
    protected TableColumn<PersonInMovie,String> PIM_positionCol;


    // List of persons involved with movie making
    // Dynamically added to the list
    private ObservableList<PersonInMovie> personsInMovies = FXCollections.observableArrayList();


    // UI elements
    protected Stage primaryStage;
    protected Scene previousScene;

    // DB Connection
    protected DBConnector dbConnector;


    // Initialize combo boxes and controls in the view.
    // Is called after the construction of the Scene
    @FXML
    void init() throws SQLException {

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
        personsTable.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    addNewPersonInMovie(row.getItem(), roleField.getText(), positionCombo.getValue());
                }
            });
            return row;
        });

        // Pair the columns of the table to the actual values of Person
        firstNameCol.setCellValueFactory(celldate -> celldate.getValue().firstNameProperty());
        lastNameCol.setCellValueFactory(celldate -> celldate.getValue().lastNameProperty());
        ageCol.setCellValueFactory(cl -> cl.getValue().ageProperty().asObject());

        // Pair the columns of the table for People involved in movie
        PIM_firstNameCol.setCellValueFactory(celldate -> celldate.getValue().firstNameProperty());
        PIM_lastNameCol.setCellValueFactory(celldate -> celldate.getValue().lastNameProperty());
        PIM_positionCol.setCellValueFactory(celldate -> celldate.getValue().positionProperty());

    }

    // Load previous scene as it was left without change
    @FXML
    void getPreviousScene(ActionEvent event) {
        primaryStage.setScene(previousScene);
    }

    // Look in the DB for people with Last name as user inserted
    @FXML
    void lookForLastName(ActionEvent event) throws SQLException {

        // Reset info label to empty string.
        infoLabel.setText("");

        // Input Last name
        // Convert to upper case to ignore lower or upper case input by user
        String lastName = personLastNameField.getText();
        // DB is searching in uppercase if user input lowercase last name
        String upperLastName = lastName.toUpperCase();



        String selectQuery = "SELECT f.id, f.meno, f.priezvisko, EXTRACT(YEARS FROM age(current_date, datum_narodenia)) AS vek FROM osoba f\n" +
                "WHERE upper(f.priezvisko) LIKE '" + upperLastName + "%'\n" +
                ";\n";
        ObservableList<Person> personObserv = dbConnector.select(selectQuery, new Parser() {
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

        // Display result (Persons) in the table
        personsTable.setItems(personObserv);

        // Inform User if Person doesn't exist.
        if (personObserv.size() > 0) {
        } else {
            infoLabel.setText("Nenasli sa ziadne postavy s hladanym priezviskom.");
        }
    }



    // Insert data into database
    // Run transaction to insert info about movie and about list of persons in movie
    @FXML
    void saveMovie(ActionEvent event) throws SQLException {


        // Load informations about movie from input fields
        String title = titleField.getText();
        Double rating = Double.parseDouble(ratingField.getText());
        int minutes = Integer.parseInt(minutesField.getText());
        int year = Integer.parseInt(yearField.getText());
        String description = descriptionField.getText();
        String language = languageCombo.getValue().toString();
        int language_id = languageCombo.getValue().getId();
        String genre = genreCombo.getValue().getName();
        int genre_id = genreCombo.getValue().getId();
        Date premiere = java.sql.Date.valueOf(premieraDatePicker.getValue());

        // Create movie with data from user input
        Movie movie = new Movie(0, title, genre, minutes, language, year, rating, description, premiere, language_id, genre_id);

        // Run transaction where you insert movie and every person to DB
        dbConnector.insertTransaction(movie, personsInMovies);

        // Go to previous scene
        getPreviousScene(null);
    }


    // Dynamically create and add Person to list of Persons in the movies.
    protected void addNewPersonInMovie(Person person, String role, Position position) {

        // Create new Person In Movie
        PersonInMovie newPersonInMovie = new PersonInMovie(person, role, position.toString(), person.getId(), position.getId() );

        // Add to oservable list
        personsInMovies.add(newPersonInMovie);

        // Display actual list of Persons in movie
        personsInMovieTable.setItems(personsInMovies);
    }


    // Get List of actually used Genres from DB
    // Return them in the observable list
    protected ObservableList<Genre> getGenresDB() throws SQLException {
        ObservableList<Genre> genresObser;
        String selectQuery = "SELECT id, nazov FROM zaner;\n";
        genresObser = dbConnector.select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Genre(
                        rs.getInt("id"),
                        rs.getString("nazov")
                );
            }
        });

        return genresObser;
    }


    // Dynamically remove Person from List of movies before the data is send to the DB
    @FXML
    void deleteSelectedPersonInMovie(){
        int deletedIndex = personsInMovieTable.getSelectionModel().getSelectedIndex();
        personsInMovies.remove(deletedIndex);
        personsInMovieTable.setItems(personsInMovies);
    }

    // Get List of actually used Languages from DB
    // Return them in the observable list
    protected ObservableList<Language> getLanguagesDB() throws SQLException {

        // Get languages
        ObservableList<Language> languagesObs;
        String selectQuery = "SELECT id, skratka FROM krajina_povodu;\n";
        languagesObs = dbConnector.select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Language(
                        rs.getInt("id"),
                        rs.getString("skratka")
                );
            }
        });

        return languagesObs;

    }

    // Get List of actually used work positions in DB
    // Return them in the observable list
    protected ObservableList<Position> getPositionsDB() throws SQLException {
        ObservableList<Position> observableList;
        String selectQuery = "SELECT id, nazov FROM obsadenie;\n";
        observableList = dbConnector.select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Position(
                        rs.getInt("id"),
                        rs.getString("nazov")
                );
            }
        });

        return observableList;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void setDbConnector(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }
}
