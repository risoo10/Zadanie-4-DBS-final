package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import sample.dbmanagment.DBConnector;
import sample.dbmanagment.MovieParser;
import sample.dbmanagment.PersonInMovieParser;
import sample.model.Movie;
import sample.model.PersonInMovie;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by Riso on 4/26/2017.
 */
public class EditMovieController extends NewMovieController{





    // Movie reference
    private Movie movie;


    @FXML
    void deleteMovie() throws SQLException {

        // Run delete statement in DB for movie and all other mentions
        // will delete on cascade by freign key constraint.
        String deleteStatement = "DELETE FROM film WHERE id="+movie.getId()+";";

        new DBConnector().execute(deleteStatement);

        getPreviousScene(null);
    }

    // Initialize Combo boxes and set tables from super class

    @FXML
    void initialize() throws SQLException {

        // Get genres and set genre COMBOBOX
        genreCombo.setItems(getGenresDB());
        genreCombo.getSelectionModel().selectFirst();

        // Get languages and set language COMBOBOX
        languageCombo.setItems(getLanguagesDB());
        languageCombo.getSelectionModel().selectFirst();

        // Pair the columns of the table for People involved in movie
        PIM_firstNameCol.setCellValueFactory(celldate -> celldate.getValue().firstNameProperty());
        PIM_lastNameCol.setCellValueFactory(celldate -> celldate.getValue().lastNameProperty());
        PIM_positionCol.setCellValueFactory(celldate -> celldate.getValue().positionProperty());
    }

    // Save movie and update the database with new values
    @FXML
    void saveEditedMovie() throws SQLException {

        // Load informations about movie from input fields
        String title = titleField.getText();
        Double rating = Double.parseDouble(ratingField.getText());
        int minutes = Integer.parseInt(minutesField.getText());
        int year = Integer.parseInt(yearField.getText());
        String description = descriptionField.getText();
        int language_id = languageCombo.getValue().getId();
        int genre_id = genreCombo.getValue().getId();
        Date premiera = java.sql.Date.valueOf(premieraDatePicker.getValue());

        String update = "UPDATE film SET nazov= '"+title+"', " +
                "hodnotenie_imdb="+rating+"," +
                "dlzka_min="+minutes+"," +
                "rok_vydania="+year+"," +
                "popis='"+description+"'," +
                "krajina_povodu_id="+language_id+"," +
                "zaner_id="+genre_id+"," +
                "premiera='" + premiera.toString() +"' "+
                "WHERE id="+movie.getId()+";";

        new DBConnector().execute(update);

        // Go to previous scene
        getPreviousScene(null);
    }

    // Init view with values from movie
    public void initValues(int movieId) throws SQLException {

        // Load movie from dbmanagment and create movie object
        // Select string
        String selectQuery = "SELECT f.id, f.nazov, hodnotenie_imdb, dlzka_min, rok_vydania, popis, k.skratka AS jazyk, z.nazov AS zaner, premiera,krajina_povodu_id, zaner_id FROM film f\n" +
                "JOIN krajina_povodu k ON k.id = f.krajina_povodu_id\n" +
                "JOIN zaner z ON z.id = f.zaner_id\n" +
                "WHERE f.id = "+ movieId + ";";

        // Recieve data from database
        movie = (Movie) new DBConnector().select(selectQuery, new MovieParser()).get(0);

        // Display data to the fields for user to edit
        titleField.setText(movie.getName());
        ratingField.setText(Double.toString(movie.getRating()));
        minutesField.setText(Integer.toString(movie.getMinutes()));
        yearField.setText(Integer.toString(movie.getYear()));
        descriptionField.setText(movie.getDescription());
        genreCombo.getSelectionModel().select(movie.getGenre_id());
        languageCombo.getSelectionModel().select(movie.getLanguage_id());
        // Set date
        premieraDatePicker.setValue(movie.getPremiera().toLocalDate());


        // Init table of persons in the movie
        // Load persons acting in / creating the movie.
        String select =
                "SELECT ovf.id, o.meno, o.priezvisko, ob.nazov, ovf.meno_postavy FROM osoba_vofilme ovf \n" +
                        "JOIN osoba o ON o.id = ovf.osoba_id \n" +
                        "JOIN obsadenie ob ON ob.id = ovf.obsadenie_id \n" +
                        "WHERE ovf.film_id ="+movieId+";\n";
        ObservableList<PersonInMovie> persons = new DBConnector().select(select, new PersonInMovieParser());





    }

}
