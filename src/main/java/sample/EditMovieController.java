package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Genre;
import sample.model.Language;
import sample.model.Movie;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by Riso on 4/26/2017.
 */
public class EditMovieController extends NewMovieController{


    @FXML
    void initialize() throws SQLException {
        super.initialize();
    }


    // Movie reference
    private Movie movie;



    // Save movie and update the database with new values
    @FXML
    void saveMovie(){


        // Go to previous scene
        getPreviousScene(null);
    }

    // Init view with values from movie
    public void initValues(int movieId) throws SQLException {

        // Load movie from db and create movie object
        // Select string
        String selectQuery = "SELECT f.id, f.nazov, hodnotenie_imdb, dlzka_min, rok_vydania, popis, k.skratka AS jazyk, z.nazov AS zaner, premiera FROM film f\n" +
                "JOIN krajina_povodu k ON k.id = f.krajina_povodu_id\n" +
                "JOIN zaner z ON z.id = f.zaner_id\n" +
                "WHERE f.id = "+ movieId + ";";

        // Recieve data from database
        movie = (Movie) new DBConnector().select(selectQuery, new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                return new Movie(
                        rs.getInt("id"),
                        rs.getString("nazov"),
                        rs.getString("zaner"),
                        rs.getInt("dlzka_min"),
                        rs.getString("jazyk"),
                        rs.getInt("rok_vydania"),
                        rs.getDouble("hodnotenie_imdb"),
                        rs.getString("popis"),
                        rs.getDate("premiera")
                );
            }
        }).get(0);

        // Display data to the fields for user to edit
        titleField.setText(movie.getName());
        ratingField.setText(Double.toString(movie.getRating()));
        minutesField.setText(Integer.toString(movie.getMinutes()));
        yearField.setText(Integer.toString(movie.getYear()));
        descriptionField.setText(movie.getDescription());

        // Select correct genre
        Genre genre = null;
        for(Genre g : genreCombo.getItems()){
            if(g.getName().equals(movie.getGenre())){
                genre = g;
                break;
            }
        }
        genreCombo.getSelectionModel().select(genre);

        // Select correct language
        Language language = null;
        for(Language l : languageCombo.getItems()){
            if(l.toString().equals(movie.getLanguage())){
                language = l;
                break;
            }
        }
        languageCombo.getSelectionModel().select(language);

        // Set date
        premieraDatePicker.setValue(movie.getPremiera().toLocalDate());


    }

}
