package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dbmanagment.DBConnector;
import sample.dbmanagment.MovieParser;
import sample.dbmanagment.Parser;
import sample.dbmanagment.PersonInMovieParser;
import sample.model.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Riso on 4/15/2017.
 */
public class UserController {


    // Output Labels for details
    @FXML
    private Label movieRating;
    @FXML
    private Label movieYear;
    @FXML
    private Label movieDesciption;
    @FXML
    private Label movieMinutes;
    @FXML
    private Label movieTitle;
    @FXML
    private Label moviePremiere;
    @FXML
    private Label movieGenre;
    @FXML
    private Label movieLanguage;

    // Input field
    @FXML
    private TextField creatorsYearField;


    // Table to display top creators
    @FXML
    private TableView<TopCreator> topCreatorsTable;
    @FXML
    private TableColumn<TopCreator, Integer> TC_movieCountCol;
    @FXML
    private TableColumn<TopCreator, String> TC_firstNameCol;
    @FXML
    private TableColumn<TopCreator, String> TC_lastNameCol;

    // Table to show newest movies
    @FXML
    private TableView<ThumbnailMovie> newestMoviesTable;
    @FXML
    private TableColumn<ThumbnailMovie, Double> MOV_ratingCol;
    @FXML
    private TableColumn<ThumbnailMovie, String> MOV_titleCol;
    @FXML
    private TableColumn<ThumbnailMovie, String> MOV_languageCol;

    // Show ongoing screenings for selected movie in the cinamas
    @FXML
    private TableView<Screening> screeningsTable;
    @FXML
    private TableColumn<Screening, Double> SCR_fullPriceCol;
    @FXML
    private TableColumn<Screening, String> SCR_cinemaCol;
    @FXML
    private TableColumn<Screening, Double> SCR_studentsPriceCol;
    @FXML
    private TableColumn<Screening, String> SCR_startCol;

    // Table to list people involved in movie making
    @FXML
    private TableView<PersonInMovie> personsInMovieTable;
    @FXML
    private TableColumn<PersonInMovie, String> PIM_roleCol;
    @FXML
    private TableColumn<PersonInMovie, String> PIM_lastNameCol;
    @FXML
    private TableColumn<PersonInMovie, String> PIM_positionCol;
    @FXML
    private TableColumn<PersonInMovie, String> PIM_firstNameCol;

    // DB connection reference
    private DBConnector dbConnector;

    // UI elements
    private Stage primaryStage;
    private Scene scene;

    // Paging
    private int limit = 100;
    private int page = 0;


    // Initialize table data and labels in the Scene
    // Is called right after construction of controller.
    @FXML
    void init() throws SQLException {


        // Set properties of columns in table for Newest Movies
        MOV_languageCol.setCellValueFactory(c->c.getValue().languageProperty());
        MOV_ratingCol.setCellValueFactory(c->c.getValue().ratingProperty().asObject());
        MOV_titleCol.setCellValueFactory(c->c.getValue().nameProperty());

        // Get data for newest movie table
        initNewestMovieTable();


        // Display info in the table.
        PIM_firstNameCol.setCellValueFactory(c->c.getValue().firstNameProperty());
        PIM_lastNameCol.setCellValueFactory(c->c.getValue().lastNameProperty());
        PIM_positionCol.setCellValueFactory(c->c.getValue().positionProperty());
        PIM_roleCol.setCellValueFactory(c->c.getValue().movieNameProperty());

        // Set click event to show details about that movie
        // Catch exception
        newestMoviesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        showMovieDetail(newValue.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (NullPointerException n){

                    }
                }
        );

        // Set properties of columns in table for Top Creators
        TC_firstNameCol.setCellValueFactory(c->c.getValue().firstNameProperty());
        TC_lastNameCol.setCellValueFactory(c->c.getValue().lastNameProperty());
        TC_movieCountCol.setCellValueFactory(c->c.getValue().movieCountProperty().asObject());

        // Set properties of columns in table for Screenings
        SCR_startCol.setCellValueFactory(c->c.getValue().startingTimeProperty());
        SCR_cinemaCol.setCellValueFactory(c->c.getValue().cinemaProperty());
        SCR_fullPriceCol.setCellValueFactory(c->c.getValue().priceProperty().asObject());
        SCR_studentsPriceCol.setCellValueFactory(c->c.getValue().studentPriceProperty().asObject());

    }


    // Pull data from DBand display top 15 creators for selected year
    // Process input year and display data to table
    @FXML
    public void showTopCreatorsForYear(ActionEvent event) throws SQLException{
        int year = Integer.parseInt(creatorsYearField.getText());
        initTopCreatorsTable(year);
    }

    // Get data about n
    private void initNewestMovieTable() throws SQLException {

        // Get newest movie data from DB for actual page
        // Pulling just few columns to have just quick look at movie
        ObservableList<ThumbnailMovie> moviesObs;
        String selectQuery = "SELECT f.id, f.nazov AS nazov, f.hodnotenie_imdb AS hodnotenie, k.skratka AS jazyk FROM film f \n" +
                "JOIN krajina_povodu k ON k.id = f.krajina_povodu_id\n" +
                "ORDER BY premiera DESC\n" +
                "LIMIT " + limit +" OFFSET "+ page*limit +";\n"; // prva strana sa oznacuje ako 0, preto offset neposunie vysledky

        // Pull data from connection to observable list
        moviesObs = dbConnector.select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new ThumbnailMovie(
                        rs.getInt("id"),
                        rs.getString("nazov"),
                        rs.getDouble("hodnotenie"),
                        rs.getString("jazyk")
                );
            }
        });

        // Display data in the table
        newestMoviesTable.setItems(moviesObs);

        // Display detailed info about first item in the table
        newestMoviesTable.getSelectionModel().select(0);
        showMovieDetail(newestMoviesTable.getSelectionModel().getSelectedItem().getId());
    }


    // Get list of top creators from DB and display them in the table.
    private void initTopCreatorsTable(int year) throws SQLException {


        String selectQuery = "SELECT o.meno, o.priezvisko, count(ovf.film_id) AS pocet FROM osoba_vofilme ovf\n" +
                "JOIN (SELECT id FROM film f WHERE EXTRACT(YEAR FROM f.premiera) = "+year+" ) tmp ON tmp.id = ovf.film_id\n" +
                "JOIN osoba o ON o.id = ovf.osoba_id\n" +
                "GROUP BY o.id\n" +
                "ORDER BY count(DISTINCT ovf.film_id) DESC, priezvisko ASC \n" +
                "LIMIT 15;";

        // Get observable list with data from DB
        ObservableList<TopCreator> topCreators = dbConnector.select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new TopCreator(rs.getInt("pocet"), new Person(0, rs.getString("meno"), rs.getString("priezvisko"), 0));
            }
        });

        // Display list in the table
        topCreatorsTable.setItems(topCreators);

    }


    // Is Called when User select row int the newest movie table
    //
    private void showMovieDetail(int movie_id) throws SQLException {

        // References for selected movie, and lists about movie makers and screenings
        Movie movie;
        ObservableList<PersonInMovie> personsInMovies = FXCollections.observableArrayList();
        ObservableList<Screening> screenings = FXCollections.observableArrayList();

        // Load simple info about movie.
        // Select string
        String selectQuery = "SELECT f.id, krajina_povodu_id, zaner_id, f.nazov, hodnotenie_imdb, dlzka_min, rok_vydania, popis, k.skratka AS jazyk, z.nazov AS zaner, premiera FROM film f\n" +
                "JOIN krajina_povodu k ON k.id = f.krajina_povodu_id\n" +
                "JOIN zaner z ON z.id = f.zaner_id\n" +
                "WHERE f.id = "+ movie_id + ";";

        // Recieve data from database
        movie = (Movie) dbConnector.select(selectQuery, new MovieParser()).get(0);

        // Display data about Movie to the labels
        movieTitle.setText(movie.getName());
        movieGenre.setText(movie.getGenre());
        movieLanguage.setText(movie.getLanguage());
        movieMinutes.setText("" + movie.getMinutes());
        movieRating.setText("" + movie.getRating());
        movieYear.setText("" + movie.getYear());
        movieDesciption.setText("" + movie.getDescription());
        moviePremiere.setText(movie.getPremiera().toString());


        // Load persons acting in / creating the movie.
        String select =
                "SELECT ovf.id, o.meno, ovf.osoba_id, ovf.obsadenie_id,  o.priezvisko, ob.nazov, ovf.meno_postavy FROM osoba_vofilme ovf \n" +
                "JOIN osoba o ON o.id = ovf.osoba_id \n" +
                "JOIN obsadenie ob ON ob.id = ovf.obsadenie_id \n" +
                "WHERE ovf.film_id ="+movie_id+";\n";
        ObservableList<PersonInMovie> persons = dbConnector.select(select, new PersonInMovieParser());
        // Show data in the table
        personsInMovieTable.setItems(persons);



        // Load movie screenings in cinemas.
        // Select statement
        select = "\n" +
                "SELECT p.id, k.nazov, EXTRACT(HOUR from p.zaciatok)AS hodiny,EXTRACT(MINUTE from p.zaciatok)AS minuty, p.cena, p.cena_student  FROM premietanie p\n" +
                "JOIN kino k ON p.kino_id = k.id\n" +
                "WHERE p.film_id = "+movie_id+";\n";
        ObservableList<Screening> screeningsMovie = dbConnector.select(select, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Screening(
                        rs.getInt("id"),
                        rs.getString("nazov"),
                        rs.getInt("hodiny") + ":" + rs.getInt("minuty"),
                        rs.getDouble("cena"),
                        rs.getDouble("cena_student")
                );
            }
        });

        // Display data in the table.
        screeningsTable.setItems(screeningsMovie);
    }


    // Load the admin page and send context info to admin controller.
    @FXML
    void showAdminPane(ActionEvent event) throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("admin_pane.fxml"));
            AnchorPane root = null;
            root = (AnchorPane) loader.load();

            // Initialize controller
            AdminController ac = loader.getController();
            ac.setPrimaryStage(primaryStage);

            // Set DB connection
            ac.setDbConnector(dbConnector);
            ac.init();

            Scene scene = new Scene(root);
            ac.setScene(scene);
            ac.setPreviousScene(this.scene);
            primaryStage.setTitle("Filmový portál - Administrácia");
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // PAGING
    // Load next page
    @FXML
    void showNextMovies(ActionEvent event) throws SQLException {
        page++;

        initNewestMovieTable();
    }
    // Load previous page
    @FXML
    void showPreviousMovies(ActionEvent event) throws SQLException {
        if(page > 0){
            page--;

            initNewestMovieTable();
        }
    }


    // Refresh movie table with actual data from database
    @FXML
    void refreshScene(ActionEvent event) throws SQLException {
        init();
    }

    // Getters and Setters

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setDbConnector(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }
}
