package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Riso on 4/15/2017.
 */
public class UserController {
    @FXML
    private TableColumn<?, ?> SCR_startCol;

    @FXML
    private TableColumn<?, ?> PIM_roleCol;

    @FXML
    private TableColumn<?, ?> MOV_ratingCol;

    @FXML
    private TableView<?> personsInMovieTable;

    @FXML
    private TableColumn<?, ?> TC_lastNameCol;

    @FXML
    private Label movieRating;

    @FXML
    private Label movieYear;

    @FXML
    private TableView<?> newestMoviesTable;

    @FXML
    private Label movieDesciption;

    @FXML
    private Label movieMinutes;

    @FXML
    private TableColumn<?, ?> SCR_cinemaCol;

    @FXML
    private TableView<?> topCreatorsTable;

    @FXML
    private Label movieTitle;

    @FXML
    private TableView<?> screeningsTable;

    @FXML
    private Label movieTitle1;

    @FXML
    private TableColumn<?, ?> TC_firstNameCol;

    @FXML
    private TableColumn<?, ?> PIM_lastNameCol;

    @FXML
    private Label movieGenre;

    @FXML
    private TableColumn<?, ?> SCR_studentsPriceCol;

    @FXML
    private Label usernameLabel;

    @FXML
    private TableColumn<?, ?> MOV_titleCol;

    @FXML
    private TableColumn<?, ?> TC_movieCountCol;

    @FXML
    private Label movieLanguage;

    @FXML
    private Label movieTitle11;

    @FXML
    private TableColumn<?, ?> PIM_firstNameCol;

    @FXML
    private TableColumn<?, ?> MOV_languageCol;

    @FXML
    private TableColumn<?, ?> SCR_fullPriceCol;

    @FXML
    private TableColumn<?, ?> PIM_positionCol;

    @FXML
    void showFavouriteLists(ActionEvent event) {

    }

    @FXML
    void showNextMovies(ActionEvent event) {

    }

    @FXML
    void showPreviousMovies(ActionEvent event) {

    }
}
