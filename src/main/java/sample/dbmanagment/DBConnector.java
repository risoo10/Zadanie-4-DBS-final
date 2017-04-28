package sample.dbmanagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Movie;
import sample.model.PersonInMovie;

import java.sql.*;

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


    public ObservableList select(String selectQuery, Parser parser) throws SQLException {


        ObservableList result = FXCollections.observableArrayList();


        try {
            con = DriverManager.getConnection(url, user, passwd);
            // use connection
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                result.add(parser.parseRow(rs));
            }

            return result;

        } catch (SQLException e) {
            // log error
            e.printStackTrace();
        } finally {
            stmt.close();
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }

        return null;
    }


    // Transaction for inserting movie with list of persons involved in making the movie
    public void insertTransaction(Movie movie, ObservableList<PersonInMovie> persons) throws SQLException {
        try {

            con = DriverManager.getConnection(url, user, passwd);
            // Stop autocommit
            con.setAutoCommit(false);

            // Insert movie
            String insertMovie = "INSERT INTO film (nazov, premiera, hodnotenie_imdb, krajina_povodu_id, zaner_id, dlzka_min, rok_vydania, popis) \n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            pstmt = con.prepareStatement(insertMovie);
            pstmt.setString(1, movie.getName());
            pstmt.setDate(2, movie.getPremiera());
            pstmt.setDouble(3, movie.getRating());
            pstmt.setInt(4, movie.getLanguage_id());
            pstmt.setInt(5, movie.getGenre_id());
            pstmt.setInt(6, movie.getMinutes());
            pstmt.setInt(7, movie.getYear());
            pstmt.setString(8, movie.getDescription());

            pstmt.execute();


            // Insert every person involved in movie
            String insertPerson = "INSERT INTO osoba_vofilme(film_id, osoba_id, obsadenie_id, meno_postavy) VALUES (currval('film_id_seq'), ?, ?, ?)";
            pstmt = con.prepareStatement(insertPerson);
            for(PersonInMovie pim : persons){
                pstmt.setInt(1, pim.getPerson_id());
                pstmt.setInt(2, pim.getPosition_id());
                pstmt.setString(3, pim.getMovieName());
                pstmt.execute();
            }

            // Commit changes
            con.commit();

        } catch (SQLException e) {
            // log error
            e.printStackTrace();
        } finally {
            pstmt.close();
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }


    }


    public void insert(String insertStatement, Inserter inserter) throws SQLException {
        try {
            con = DriverManager.getConnection(url, user, passwd);
            // use connection

            pstmt = con.prepareStatement(insertStatement);

            inserter.insertRows(pstmt);

        } catch (SQLException e) {
            // log error
            e.printStackTrace();
        } finally {
            pstmt.close();
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }


    public void execute(String exetutedStatement) throws SQLException {
        try {
            con = DriverManager.getConnection(url, user, passwd);
            // use connection

            pstmt = con.prepareStatement(exetutedStatement);

            pstmt.execute();

        } catch (SQLException e) {
            // log error
            e.printStackTrace();
        } finally {
            pstmt.close();
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}

