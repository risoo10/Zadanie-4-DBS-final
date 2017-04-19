package sample.model;

import javafx.beans.property.*;

/**
 * Created by Riso on 4/15/2017.
 */
public class Screening {

    private final IntegerProperty id;
    private final IntegerProperty movieId;
    private final StringProperty cinema;
    // Screening time
    private final DoubleProperty price;
    private final DoubleProperty studentPrice;

    public Screening(int id, int movieId, String cinema, Double price, Double studentPrice) {
        this.id = new SimpleIntegerProperty(id);
        this.movieId = new SimpleIntegerProperty(movieId);
        this.cinema = new SimpleStringProperty(cinema);
        this.price = new SimpleDoubleProperty(price);
        this.studentPrice = new SimpleDoubleProperty(studentPrice);
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

    public int getMovieId() {
        return movieId.get();
    }

    public IntegerProperty movieIdProperty() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId.set(movieId);
    }

    public String getCinema() {
        return cinema.get();
    }

    public StringProperty cinemaProperty() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema.set(cinema);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getStudentPrice() {
        return studentPrice.get();
    }

    public DoubleProperty studentPriceProperty() {
        return studentPrice;
    }

    public void setStudentPrice(double studentPrice) {
        this.studentPrice.set(studentPrice);
    }
}
