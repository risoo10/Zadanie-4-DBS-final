package sample.dbmanagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import sample.model.Movie;
import sample.model.PersonInMovie;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by Riso on 4/15/2017.
 */
public class DBConnector {

    // Data source which manage getting connection
    private PGSimpleDataSource source = new PGSimpleDataSource();

    //
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private Connection con = null;

    // Initialize values important for establishing connection
    public DBConnector() {
        source.setServerName("localhost");
        source.setDatabaseName("filmovy_portal");
        source.setUser("film_admin");
        source.setPassword("123456");
    }

    // General method for selecting data from DB
    // **************
    // Select query will be executed and the Parser will process the result set
    // Parser will be passed as object with implemented method parseRow()
    // Requested data will be returned as a observable list of objects
    public ObservableList select(String selectQuery, Parser parser) throws SQLException {

        // Get empty observable list
        ObservableList result = FXCollections.observableArrayList();

        //
        try {
            // Get Connection
            con = source.getConnection();
            // Create statement
            stmt = con.createStatement();
            // Execute Statement with selected query
            ResultSet rs = stmt.executeQuery(selectQuery);

            // Process every row with Parser object
            while (rs.next()) {
                result.add(parser.parseRow(rs));
            }

            // Return list of objects
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

    // Specific transaction
    // Transaction for inserting movie with list of persons involved in making the movie.
    // Movie collects basic info about movie
    public void insertTransaction(Movie movie, ObservableList<PersonInMovie> persons) throws SQLException {
        try {
            // Get Connection
            con = source.getConnection();
            // Stop autocommit to gain atomic structure of trabsaction
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

            // Commit changes and close atomic structure
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


    // General method to execute SQL statements
    public void execute(String exetutedStatement) throws SQLException {
        try {

            // Get connection and execute statement
            con = source.getConnection();
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

