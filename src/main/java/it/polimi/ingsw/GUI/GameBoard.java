package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameBoard extends Application {

    @Override
    public void start(Stage stage) throws Exception {



        BorderPane root = new BorderPane();
        Scene scene = new Scene (root);
        stage.setFullScreen(true);

        stage.setTitle("Eryantis");
        stage.setScene(scene);

        ImageView img_1 = new ImageView(new Image(getClass().getResourceAsStream("File: island1.png")));
        Button button = new Button("prova",img_1);
        root.setCenter(button);

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
