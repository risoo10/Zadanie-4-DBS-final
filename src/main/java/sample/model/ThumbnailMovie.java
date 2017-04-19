package sample.model;

import javafx.beans.property.*;

/**
 * Created by Riso on 4/15/2017.
 */
public class ThumbnailMovie {

    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty year;
    private final DoubleProperty rating;
    private final StringProperty language;

    /**
     *
     *
     * @param id
     * @param name
     * @param year
     * @param rating
     * @param language
     */
    public ThumbnailMovie(int id, String name, int year, Double rating, String language) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
        this.rating = new SimpleDoubleProperty(rating);
        this.language = new SimpleStringProperty(language);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getLanguage() {
        return language.get();
    }

    public StringProperty languageProperty() {
        return language;
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public double getRating() {
        return rating.get();
    }

    public DoubleProperty ratingProperty() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating.set(rating);
    }
}
