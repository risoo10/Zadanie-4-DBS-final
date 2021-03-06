package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dbmanagment.DBConnector;
import sample.dbmanagment.Parser;
import sample.model.ThumbnailMovie;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Riso on 4/15/2017.
 */
public class AdminController {


    // Controls for Table with movies ordered by Premiere date
    @FXML
    private TableView<ThumbnailMovie> newestMoviesTable;
    @FXML
    private TableColumn<ThumbnailMovie, String> MOV_titleCol;
    @FXML
    private TableColumn<ThumbnailMovie, String> MOV_languageCol;
    @FXML
    private TableColumn<ThumbnailMovie, Double> MOV_ratingCol;

    @FXML
    private TextField movieTitleSearchField;

    // Controls for Table with movies results for search by title
    @FXML
    private TableView<ThumbnailMovie> searchMovieTable;
    @FXML
    private TableColumn<ThumbnailMovie, String> SEARCH_titleCol;
    @FXML
    private TableColumn<ThumbnailMovie, String> SEARCH_languageCol;
    @FXML
    private TableColumn<ThumbnailMovie, Double> SEARCH_ratingCol;


    // Paging - when desired btn pressed move to previous/next page
    // Paging for table with newest movies and also for table for Searching movies by title
    private int orderedLimit = 100;
    private int orderPage = 0;

    private int searchLimit = 100;
    private int searchPage = 0;


    // UI elements
    private Stage primaryStage;
    private Scene previousScene;
    private Scene scene;

    // Search string saved for usage
    private String searchTitle;

    // DB connection
    private DBConnector dbConnector;


    // Initialize values and tables
    // Is called when scene is constructed
    @FXML
    void init() throws SQLException {

        // Set properties of columns in table for Newest Movies
        MOV_languageCol.setCellValueFactory(c->c.getValue().languageProperty());
        MOV_ratingCol.setCellValueFactory(c->c.getValue().ratingProperty().asObject());
        MOV_titleCol.setCellValueFactory(c->c.getValue().nameProperty());
        initNewestMovieTable();

        // Set properties of columns for Search results table
        SEARCH_languageCol.setCellValueFactory(c->c.getValue().languageProperty());
        SEARCH_ratingCol.setCellValueFactory(c->c.getValue().ratingProperty().asObject());
        SEARCH_titleCol.setCellValueFactory(c->c.getValue().nameProperty());

        // Listen to double click on the table row and launch Editing Scene
        setDoubleClickAction(newestMoviesTable);
        setDoubleClickAction(searchMovieTable);

    }

    // Set the action which happens after user double click on the row in the table.
    // After double click load the Scene for editing movie
    private void setDoubleClickAction(TableView table){

        // Listen to double click on the table row and launch Editing Scene
        table.setRowFactory(tv -> {
            TableRow<ThumbnailMovie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {

                        //
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getClassLoader().getResource("edit_movie.fxml"));
                        AnchorPane root = null;
                        root = (AnchorPane) loader.load();
                        EditMovieController emc = loader.getController();
                        emc.setPrimaryStage(primaryStage);
                        emc.setPreviousScene(this.scene);

                        // Send DB connection
                        emc.setDbConnector(dbConnector);
                        emc.init();

                        emc.initValues(row.getItem().getId());
                        primaryStage.setTitle("Filmový portál - Uprav film");
                        primaryStage.setScene(new Scene(root));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }

    // Initialize table with list of newest movies from DB
    private void initNewestMovieTable() throws SQLException {

        // Get data from DB for actual page
        ObservableList<ThumbnailMovie> moviesObs;
        String selectQuery = "SELECT f.id, f.nazov AS nazov, f.hodnotenie_imdb AS hodnotenie, k.skratka AS jazyk FROM film f \n" +
                "JOIN krajina_povodu k ON k.id = f.krajina_povodu_id\n" +
                "ORDER BY premiera DESC\n" +
                "LIMIT " + orderedLimit +" OFFSET "+ orderPage*orderedLimit +";\n"; // prva strana sa oznacuje ako 0, preto offset neposunie vysledky

        // Set observable list with list of movies from DB
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

        // Show data in the table
        newestMoviesTable.setItems(moviesObs);

    }


    // PAGING - manage pages of NEWEST MOVIE table
    // Load previous page
    @FXML
    void showPreviousMoviesNewest(ActionEvent event) throws SQLException {
        if(orderPage > 0){
            orderPage--;
            initNewestMovieTable();
        }

    }
    // Load next page
    @FXML
    void showNextMoviesNewest(ActionEvent event) throws SQLException {
        orderPage++;
        initNewestMovieTable();
    }


    // PAGING - manage pages of Search By Title table
    // Load previous page
    @FXML
    void showPreviousMoviesSearch(ActionEvent event) throws SQLException {
        if(searchPage > 0){
            searchPage--;
            showMoviesByTitle();
        }
    }
    // Load next page
    @FXML
    void showNextMoviesSearch(ActionEvent event) throws SQLException {
        searchPage++;
        showMoviesByTitle();
    }

    // Refresh results in the table with Ordered Movies
    @FXML
    void refreshMovieTable(ActionEvent event) throws SQLException {
        init();
    }


    // Show movies, where title is similar to user input from search field.
    @FXML
    void searchMovieByTitle(ActionEvent event) throws SQLException {
        searchTitle = movieTitleSearchField.getText();
        showMoviesByTitle();
    }

    // Get the results from the database by the previously set title
    private void showMoviesByTitle() throws SQLException {


        // Get search string in the uppercase
        // Databse uses index where is the movie title ordered and in the uppercase
        // to ignore upper or lower cases.
        // This brings simpler searching for user.
        String upperTitle = searchTitle.toUpperCase();

        String selectQuery = "SELECT f.id, f.nazov, f.hodnotenie_imdb, (SELECT k.skratka FROM krajina_povodu k WHERE f.krajina_povodu_id = k.id) AS jazyk  FROM film f\n" +
                "WHERE upper(f.nazov) LIKE '"+upperTitle+"%'\n" +
                "LIMIT "+searchLimit+" OFFSET "+searchPage*searchLimit+";\n";

        // Set observable list with results from database
        ObservableList<ThumbnailMovie> movieObs = dbConnector.select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new ThumbnailMovie(
                        rs.getInt("id"),
                        rs.getString("nazov"),
                        rs.getDouble("hodnotenie_imdb"),
                        rs.getString("jazyk")
                );
            }
        });

        // Show inf in the table
        searchMovieTable.setItems(movieObs);
    }


    // Start(Load) new Scene with Controls to Create new movie.
    // new scene is located in view [ add_movie.fxml ] in resource
    @FXML
    void addNewMovie(ActionEvent event) throws SQLException {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("add_movie.fxml"));
            AnchorPane root = null;
            root = (AnchorPane) loader.load();

            // Initialize controller
            NewMovieController emc = loader.getController();
            emc.setPrimaryStage(primaryStage);
            emc.setPreviousScene(this.scene);

            // Send DB connection
            emc.setDbConnector(dbConnector);
            emc.init();

            primaryStage.setTitle("Filmový portál - Pridaj nový film");
            primaryStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // Load previous Scene(home scene) as it was left before
    @FXML
    void getPreviousScene(ActionEvent event) {
        primaryStage.setScene(previousScene);
    }



    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setDbConnector(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }
}
