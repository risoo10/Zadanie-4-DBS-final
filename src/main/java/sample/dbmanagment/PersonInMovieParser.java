package sample.dbmanagment;

import sample.model.PersonInMovie;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Riso on 4/28/2017.
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
