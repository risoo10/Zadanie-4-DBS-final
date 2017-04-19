package sample.model;

import java.util.List;

/**
 * Created by Riso on 4/15/2017.
 */
public class FavouriteList {

    private int id;
    private String name;
    private List<ThumbnailMovie> movies;

    public FavouriteList(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
