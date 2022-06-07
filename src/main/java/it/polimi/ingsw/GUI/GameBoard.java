package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Table;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameBoard extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = FXMLLoader.load(getClass(). getResource("GameBoard.fxml"));
        Scene scene = new Scene (root);
        stage.setFullScreen(true);
        stage.setTitle("Eryantis");
        stage.setScene(scene);





        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
