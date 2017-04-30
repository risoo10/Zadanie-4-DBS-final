package sample.model;

import java.sql.Date;
import java.util.List;

/**
 * Entity
 * Holds info about Movie during runtime
 * Maps entity film in the Entity Model
 */
public class Movie {

    // Attributes
    private int id;
    private String name;
    private String genre;
    private int genre_id;
    private int minutes;
    private String language;
    private int language_id;
    private int year;
    private Double rating;
    private String description;
    private Date premiera;


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

    public Movie(int id, String name, String genre, int minutes, String language, int year, Double rating, String description, Date premiera, int language_id, int genre_id) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.minutes = minutes;
        this.language = language;
        this.year = year;
        this.rating = rating;
        this.description = description;
        this.premiera = premiera;
        this.language_id = language_id;
        this.genre_id = genre_id;
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

    public Date getPremiera() {
        return premiera;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public int getLanguage_id() {
        return language_id;
    }
}