package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Riso on 4/15/2017.
 */
public class ThumbnailMovie {

    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty year;
    private final IntegerProperty rating;
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
    public ThumbnailMovie(int id, String name, int year, int rating, String language) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
        this.rating = new SimpleIntegerProperty(rating);
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

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
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
}
