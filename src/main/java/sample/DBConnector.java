package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Riso on 4/15/2017.
 */
public class DBConnector {

    private final static String url = "jdbc:postgresql:filmovy_portal";
    private final static String user = "film_admin";
    private final static String passwd = "123456";


    private Statement stmt = null;
    private Connection con = null;


    public ObservableList select(String selectQuery, Parser parser) throws SQLException{


        ObservableList result = FXCollections.observableArrayList();


        try
        {
            con = DriverManager.getConnection(url, user, passwd);
            // use connection
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);

            while(rs.next()){
                result.add(parser.parseRow(rs));
            }

            return result;

        }
        catch (SQLException e)
        {
            // log error
            e.printStackTrace();
        }
        finally
        {
            stmt.close();
            if (con != null)
            {
                try { con.close(); } catch (SQLException e) {}
            }
        }

        return null;
    }

}
