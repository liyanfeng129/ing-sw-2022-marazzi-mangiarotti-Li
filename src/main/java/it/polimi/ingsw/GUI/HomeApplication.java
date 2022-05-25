package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass(). getResource("home.fxml"));
        // set up stage and scene color
        stage.setScene( new Scene(root,1500,1000));

        stage.setTitle("Eryantis");

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}