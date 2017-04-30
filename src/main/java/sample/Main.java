package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dbmanagment.DBConnector;
import sample.dbmanagment.Parser;
import sample.dbmanagment.PersonInMovieParser;
import sample.model.PersonInMovie;
import sample.model.ThumbnailMovie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Initialize Data Source
        DBConnector dbc = new DBConnector();

        // Load GUI
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("user_pane.fxml"));
        AnchorPane root = (AnchorPane) loader.load();

        // Set and initialize controller
        UserController uc = loader.getController();
        uc.setDbConnector(dbc);
        uc.setPrimaryStage(primaryStage);
        uc.init();

        // Show first scene
        primaryStage.setTitle("Filmový portál - Domov");
        Scene scene = new Scene(root);
        uc.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
