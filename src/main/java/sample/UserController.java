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

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField creatorsYearField;


    @FXML
    private TableView<TopCreator> topCreatorsTable;
    @FXML
    private TableColumn<TopCreator, Integer> TC_movieCountCol;
    @FXML
    private TableColumn<TopCreator, String> TC_firstNameCol;
    @FXML
    private TableColumn<TopCreator, String> TC_lastNameCol;


    @FXML
    private TableView<ThumbnailMovie> newestMoviesTable;
    @FXML
    private TableColumn<ThumbnailMovie, Double> MOV_ratingCol;
    @FXML
    private TableColumn<ThumbnailMovie, String> MOV_titleCol;
    @FXML
    private TableColumn<ThumbnailMovie, String> MOV_languageCol;


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


    // UI elements
    private Stage primaryStage;
    private Scene scene;


    private int limit = 100;
    private int page = 0;


    @FXML
    void initialize() throws SQLException {

        // Randomly choose user from database
        showMovieDetail(1);

        // Set properties of columns in table for Newest Movies
        MOV_languageCol.setCellValueFactory(c->c.getValue().languageProperty());
        MOV_ratingCol.setCellValueFactory(c->c.getValue().ratingProperty().asObject());
        MOV_titleCol.setCellValueFactory(c->c.getValue().nameProperty());
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
        TC_movieCountCol.setCellValueFactory(c->c.getValue().filmCountProperty().asObject());

        // Set properties of columns in table for Screenings
        SCR_startCol.setCellValueFactory(c->c.getValue().startingTimeProperty());
        SCR_cinemaCol.setCellValueFactory(c->c.getValue().cinemaProperty());
        SCR_fullPriceCol.setCellValueFactory(c->c.getValue().priceProperty().asObject());
        SCR_studentsPriceCol.setCellValueFactory(c->c.getValue().studentPriceProperty().asObject());

    }

    @FXML
    public void showTopCreatorsForYear(ActionEvent event) throws SQLException{
        int year = Integer.parseInt(creatorsYearField.getText());
        initTopCreatorsTable(year);
    }

    private void initNewestMovieTable() throws SQLException {

        // Get data from DB as actual page
        ObservableList<ThumbnailMovie> moviesObs;
        String selectQuery = "SELECT f.id, f.nazov AS nazov, f.hodnotenie_imdb AS hodnotenie, k.skratka AS jazyk FROM film f \n" +
                "JOIN krajina_povodu k ON k.id = f.krajina_povodu_id\n" +
                "ORDER BY premiera DESC\n" +
                "LIMIT " + limit +" OFFSET "+ page*limit +";\n"; // prva strana sa oznacuje ako 0, preto offset neposunie vysledky


        moviesObs = new DBConnector().select(selectQuery, new Parser() {
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

        newestMoviesTable.setItems(moviesObs);

    }

    private void initTopCreatorsTable(int year) throws SQLException {
        String selectQuery = "SELECT o.meno, o.priezvisko, count(ovf.film_id) AS pocet FROM osoba_vofilme ovf\n" +
                "JOIN (SELECT id FROM film f WHERE EXTRACT(YEAR FROM f.premiera) = "+year+" ) tmp ON tmp.id = ovf.film_id\n" +
                "JOIN osoba o ON o.id = ovf.osoba_id\n" +
                "GROUP BY o.id\n" +
                "ORDER BY count(DISTINCT ovf.film_id) DESC, priezvisko ASC \n" +
                "LIMIT 15;";
        long start = System.currentTimeMillis();
        ObservableList<TopCreator> topCreators = new DBConnector().select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new TopCreator(rs.getInt("pocet"), new Person(0, rs.getString("meno"), rs.getString("priezvisko"), 0));
            }
        });

        System.out.println(System.currentTimeMillis() - start);
        topCreatorsTable.setItems(topCreators);

    }

    private void showMovieDetail(int movie_id) throws SQLException {

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
        movie = (Movie) new DBConnector().select(selectQuery, new MovieParser()).get(0);

        // Display informations.
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
        ObservableList<PersonInMovie> persons = new DBConnector().select(select, new PersonInMovieParser());
        personsInMovieTable.setItems(persons);



        // Load screenings of the movie in the cinema.

        select = "\n" +
                "SELECT p.id, k.nazov, EXTRACT(HOUR from p.zaciatok)AS hodiny,EXTRACT(MINUTE from p.zaciatok)AS minuty, p.cena, p.cena_student  FROM premietanie p\n" +
                "JOIN kino k ON p.kino_id = k.id\n" +
                "WHERE p.film_id = "+movie_id+";\n";
        ObservableList<Screening> screeningsMovie = new DBConnector().select(select, new Parser() {
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


    @FXML
    void showAdminPane(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("admin_pane.fxml"));
            AnchorPane root = null;
            root = (AnchorPane) loader.load();
            AdminController ac = loader.getController();
            ac.setPrimaryStage(primaryStage);
            Scene scene = new Scene(root);
            ac.setScene(scene);
            ac.setPreviousScene(this.scene);
            primaryStage.setTitle("Filmový portál - Administrácia");
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void showNextMovies(ActionEvent event) throws SQLException {
        page++;

        initNewestMovieTable();
    }

    @FXML
    void showPreviousMovies(ActionEvent event) throws SQLException {
        if(page > 0){
            page--;

            initNewestMovieTable();
        }



    }

    @FXML
    void refreshScene(ActionEvent event) throws SQLException {
        initialize();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
