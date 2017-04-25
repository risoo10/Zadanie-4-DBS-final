package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.ThumbnailMovie;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Riso on 4/15/2017.
 */
public class AdminController {


    // Controls for Table with movies ordered by Premiere date
    @FXML
    private TableView<?> newestMoviesTable;
    @FXML
    private TableColumn<ThumbnailMovie, String> MOV_titleCol;
    @FXML
    private TableColumn<ThumbnailMovie, String> MOV_languageCol;
    @FXML
    private TableColumn<ThumbnailMovie, Double> MOV_ratingCol;
    @FXML
    private TableColumn<ThumbnailMovie, Button> MOV_editCol;


    @FXML
    private TextField movieTitleSearchField;

    // Controls for Table with movies results for search by title
    @FXML
    private TableView<?> searchMovieTable;
    @FXML
    private TableColumn<ThumbnailMovie, String> SEARCH_titleCol;
    @FXML
    private TableColumn<ThumbnailMovie, String> SEARCH_languageCol;
    @FXML
    private TableColumn<ThumbnailMovie, Double> SEARCH_ratingCol;
    @FXML
    private TableColumn<ThumbnailMovie, Button> SEARCH_editCol;


    // Paging - when desired btn pressed move to previous/next page
    // Paging for table with newest movies and also for table for Searching movies by title
    private int orderedLimit = 100;
    private int orderPage = 0;

    private int searchLimit = 100;
    private int searchPage = 0;

    private Stage primaryStage;


    // PAGING - manage pages of NEWEST MOVIE table
    @FXML
    void showPreviousMoviesNewest(ActionEvent event) {

    }

    @FXML
    void showNextMoviesNewest(ActionEvent event) {

    }


    // PAGING - manage pages of Search By Title table
    @FXML
    void showPreviousMoviesSearch(ActionEvent event) {

    }

    @FXML
    void showNextMoviesSearch(ActionEvent event) {

    }


    // Show movies, where title is similar to user input from search field.
    @FXML
    void searchMovieByTitle(ActionEvent event) {

    }


    // Start(Load) new Scene with Controls to Create new movie.
    // new scene is located in view [ add_movie.fxml ] in resource
    @FXML
    void addNewMovie(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("add_movie.fxml"));
            AnchorPane root = null;
            root = (AnchorPane) loader.load();
            EditMovieController emc = loader.getController();
            emc.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Filmový portál - Pridaj nový film");
            primaryStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // Load previous Scene(home scene), in the view [ user_pane.fxml ]
    @FXML
    void getPreviousScene(ActionEvent event) {

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
