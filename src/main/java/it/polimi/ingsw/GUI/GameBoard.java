package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameBoard extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Button button = new Button();
        button.setText("prova");
        root.setCenter(button);
        Scene scene = new Scene (root, Color.LIGHTSKYBLUE);
        stage.setTitle("Eryantis");
        stage.setScene(scene);
        stage.setFullScreen(true);






        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
