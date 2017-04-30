package sample.dbmanagment;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Parser is used to process row of the Result set.
 *  Every parser needs to implement method parseRow and return specific enitity object.
 *  Mainly used by creating Anonymous classes which implements method parseRow() for
 *  every specific select.
 */

public interface Parser {
    public Object parseRow(ResultSet rs) throws SQLException;
}
