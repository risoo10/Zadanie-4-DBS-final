package sample;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Riso on 4/24/2017.
 */
public interface Inserter {

    public void insertRows(PreparedStatement pstm) throws SQLException;

}
