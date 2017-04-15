package sample;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Riso on 4/15/2017.
 */
public interface Parser {
    public Object parseRow(ResultSet rs) throws SQLException;
}
