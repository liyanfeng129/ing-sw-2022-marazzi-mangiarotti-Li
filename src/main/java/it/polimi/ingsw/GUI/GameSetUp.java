package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameSetUp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass(). getResource("game_set_up.fxml"));
        // set up stage and scene color
        Scene scene = new Scene (root);
        stage.setTitle("Eryantis");
        stage.setScene(scene);
        stage.setFullScreen(true);




        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}