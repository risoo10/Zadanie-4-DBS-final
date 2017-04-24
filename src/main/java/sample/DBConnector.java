package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Movie;

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
    private PreparedStatement pstmt = null;
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

//    public void insertMovie(Movie movie, int FKgenre, int FKlanguage, Date premiera) throws SQLException {
//
//
//        try
//        {
//            con = DriverManager.getConnection(url, user, passwd);
//            // use connection
//
//            PreparedStatement stmt = con.prepareStatement(insertNewMovie);
//            stmt.setString(1, movie.getName() );
//            stmt.setDouble(2, movie.getRating());
//            stmt.setInt(3, movie.getMinutes());
//            stmt.setInt(4, movie.getYear());
//            stmt.setString(5, movie.getDescription());
//            stmt.setInt(6, FKlanguage);
//            stmt.setInt(7, FKgenre);
//            stmt.setDate(8, premiera);
//
//            stmt.execute();
//
//
//        }
//        catch (SQLException e)
//        {
//            // log error
//            e.printStackTrace();
//        }
//        finally
//        {
//            stmt.close();
//            if (con != null)
//            {
//                try { con.close(); } catch (SQLException e) {}
//            }
//        }
//
//    }


    public void insert(String insertStatement, Inserter inserter) throws SQLException {
        try
        {
            con = DriverManager.getConnection(url, user, passwd);
            // use connection

            pstmt = con.prepareStatement(insertStatement);

            inserter.insertRows(pstmt);

        }
        catch (SQLException e)
        {
            // log error
            e.printStackTrace();
        }
        finally
        {
            pstmt.close();
            if (con != null)
            {
                try { con.close(); } catch (SQLException e) {}
            }
        }
    }

}
