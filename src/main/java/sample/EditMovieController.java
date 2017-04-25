package sample;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
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

    @FXML
    private DatePicker premieraDatePicker;


    private boolean insertOperation;

    private ObservableList<PersonInMovie> personsInMovies = FXCollections.observableArrayList();


    private Stage primaryStage;
    private Scene previousScene;



    @FXML
    void getPreviousScene(ActionEvent event) {
        primaryStage.setScene(previousScene);
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
                "WHERE upper(f.priezvisko) LIKE '%" + upperLastName + "%'\n" +
                ";\n";
        ObservableList<Person> personObserv = dbc.select(selectQuery, new Parser() {
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

        // Display Persons in the table
        personsTable.setItems(personObserv);

        // Inform User if Person doesnt exist.
        if (personObserv.size() > 0) {
        } else {
            infoLabel.setText("Nenasli sa ziadne postavy s hladanim priezviskom.");
        }
    }


    // Pomocou transakcie uloz novy film do databazy.
    // Vyuzijeme transkaciu pomocou ktorej zapiseme vsetky udaje do databazy.
    @FXML
    void saveMovie(ActionEvent event) throws SQLException {


        // Load informations about movie from input fields
        String title = titleField.getText();
        Double rating = Double.parseDouble(ratingField.getText());
        int minutes = Integer.parseInt(minutesField.getText());
        int year = Integer.parseInt(yearField.getText());
        String description = descriptionField.getText();
        int language_id = languageCombo.getValue().getId();
        int genre_id = genreCombo.getValue().getId();
        Date premiera = java.sql.Date.valueOf(premieraDatePicker.getValue());

        String insertStatement = "INSERT INTO film(nazov, hodnotenie_imdb, dlzka_min, rok_vydania, popis, krajina_povodu_id, zaner_id, premiera) VALUES (?,?,?,?,?,?,?,?);";
        new DBConnector().insert(insertStatement, new Inserter() {

            @Override
            public void insertRows(PreparedStatement pstm) throws SQLException{

                pstm.setString(1, title);
                pstm.setDouble(2, rating);
                pstm.setInt(3, minutes);
                pstm.setInt(4, year);
                pstm.setString(5, description);
                pstm.setInt(6, language_id);
                pstm.setInt(7, genre_id);
                pstm.setDate(8, premiera);

                pstm.execute();
            }
        });


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
        personsTable.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    addNewPersonInMovie(row.getItem(), roleField.getText(), positionCombo.getValue());
                }
            });
            return row;
        });


    }


    // Pridaj Osobu do personalneho obsadenia.

    private void addNewPersonInMovie(Person person, String role, Position position) {

        // Create new Person In Movie
        PersonInMovie newPersonInMovie = new PersonInMovie(person, role, position.toString());

        // Add to oservable list
        personsInMovies.add(newPersonInMovie);

        // Display actual list of Persons in movie
        personsInMovieList.setText("");
        for (PersonInMovie pim : personsInMovies) {
            personsInMovieList.setText(personsInMovieList.getText() +
                    pim.getFirstName() + " " + pim.getLastName() + "  (" + pim.getPosition() + ")\n");
        }

    }


    // Get List of actually used Genres in DB
    // Return them in the observable list

    private ObservableList<Genre> getGenresDB() throws SQLException {
        ObservableList<Genre> genresObser;
        String selectQuery = "SELECT id, nazov FROM zaner;\n";
        genresObser = new DBConnector().select(selectQuery, new Parser() {
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


    private ObservableList<Language> getLanguagesDB() throws SQLException {

        // Get languages
        ObservableList<Language> languagesObs;
        String selectQuery = "SELECT id, skratka FROM krajina_povodu;\n";
        languagesObs = new DBConnector().select(selectQuery, new Parser() {
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

    private ObservableList<Position> getPositionsDB() throws SQLException {
        ObservableList<Position> observableList;
        String selectQuery = "SELECT id, nazov FROM obsadenie;\n";
        observableList = new DBConnector().select(selectQuery, new Parser() {
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
}
