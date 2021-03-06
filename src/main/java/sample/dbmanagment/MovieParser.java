package sample.dbmanagment;

import sample.model.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Parse row when selecting movie from DB.
 * Has multiple usage, so we don't need to implement the Anonymous class with parseRow() method too many times.
 */
public class MovieParser implements Parser {

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
                rs.getDate("premiera"),
                rs.getInt("krajina_povodu_id"),
                rs.getInt("zaner_id")
        );
    }

}
