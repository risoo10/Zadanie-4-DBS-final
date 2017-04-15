package sample.Model;

import javafx.beans.property.DoubleProperty;

import java.util.List;

/**
 * Created by Riso on 4/15/2017.
 */
public class Movie {

    private int id;
    private String name;
    private String genre;
    private int minutes;
    private String language;
    private int year;
    private Double rating;
    private String description;

    private List<PersonInMovie> cast;
    private List<Screening> screenings;

    /**
     *
     * @param id
     * @param name
     * @param genre
     * @param minutes
     * @param language
     * @param year
     * @param rating
     * @param description
     */

    public Movie(int id, String name, String genre, int minutes, String language, int year, Double rating, String description) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.minutes = minutes;
        this.language = language;
        this.year = year;
        this.rating = rating;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getLanguage() {
        return language;
    }

    public int getYear() {
        return year;
    }

    public Double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }
}