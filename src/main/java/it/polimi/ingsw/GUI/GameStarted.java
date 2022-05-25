package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameStarted extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass(). getResource("game_started.fxml"));
        // set up stage and scene color
        stage.setScene( new Scene(root,1500,1000));

        stage.setTitle("Eryantis");





        //Image icon = new Image("file:./Users/leonardomarazzi/Desktop/progetto_ing_sftw/wooden_pieces/island2.png");



        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}