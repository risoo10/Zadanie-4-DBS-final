package sample.dbmanagment;

import sample.model.PersonInMovie;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Parse row when selecting Person In Movie from DB.
 * Has multiple usage, so we don't need to implement the Anonymous class with parseRow() method too many times.
 */
public class PersonInMovieParser implements Parser {

    @Override
    public Object parseRow(ResultSet rs) throws SQLException {
        return new PersonInMovie(
                rs.getInt("id"),
                rs.getString("meno"),
                rs.getString("priezvisko"),
                rs.getString("nazov"),
                rs.getString("meno_postavy"),
                rs.getInt("osoba_id"),
                rs.getInt("obsadenie_id")
        );
    }
}
