package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.PersonInMovie;
import sample.Model.Screening;
import sample.Model.ThumbnailMovie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("GUI/main_window.fxml"));
//        primaryStage.setTitle("DBS 2017 - Filmovy portal");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//


        DBConnector dbc = new DBConnector();
        List<ThumbnailMovie> najnovsieFilmy = (List<ThumbnailMovie>)dbc.select("SELECT f.id, f.nazov, f.hodnotenie_imdb  FROM film f WHERE f.id = 256;", new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException{
                ThumbnailMovie newMovie = new ThumbnailMovie(
                        rs.getInt("id"),
                        rs.getString("nazov"),
                        2017,
                        rs.getDouble("hodnotenie_imdb"),
                        "EN"
                        );
                return newMovie;
            }
        });
        for (ThumbnailMovie st : najnovsieFilmy){
            System.out.println(st.getName() +"  ||  " + st.getRating() + " || "+ st.getYear());
        }

        String screeningSelect =
                "SELECT ovf.id, o.meno, o.priezvisko, ob.nazov FROM osoba_vofilme ovf \n" +
                "JOIN osoba o ON o.id = ovf.osoba_id \n" +
                "JOIN obsadenie ob ON ob.id = ovf.obsadenie_id \n" +
                "WHERE ovf.film_id =";

        List<PersonInMovie> premietania = (List<PersonInMovie>)dbc.select(screeningSelect + najnovsieFilmy.get(0).getId() + ";", new Parser() {
            @Override
            public Object parseRow(ResultSet rs) throws SQLException {
                PersonInMovie pim = new PersonInMovie(
                        rs.getInt("id"),
                        rs.getString("meno"),
                        rs.getString("priezvisko"),
                        najnovsieFilmy.get(0).getName(),
                        rs.getString("nazov")
                );

                return pim;
            }


        });

        for(PersonInMovie pim : premietania){
            System.out.println(pim.getFirstName() + "  "+pim.getLastName() + " : " + pim.getPosition() + "("+pim.getMovieName()+")");
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
