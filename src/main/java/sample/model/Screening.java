package sample.model;

import javafx.beans.property.*;

/**
 * Created by Riso on 4/15/2017.
 */
public class Screening {

    private final IntegerProperty id;
    private final StringProperty cinema;
    private final StringProperty startingTime;
    private final DoubleProperty price;
    private final DoubleProperty studentPrice;

    public Screening(int id, String cinema, String startingTime, Double price, Double studentPrice) {
        this.id = new SimpleIntegerProperty(id);
        this.cinema = new SimpleStringProperty(cinema);
        this.startingTime = new SimpleStringProperty(startingTime);
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

    public String getStartingTime() {
        return startingTime.get();
    }

    public StringProperty startingTimeProperty() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime.set(startingTime);
    }
}
