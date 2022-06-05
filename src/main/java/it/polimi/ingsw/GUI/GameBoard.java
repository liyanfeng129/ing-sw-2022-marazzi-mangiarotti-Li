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





        /**
        pos_x =0;
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        for(int i=0; i<table.getClouds().size();i++){

            ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream("image/PLANCIA_GIOCO.png")));
            img_view.setFitWidth((screenBounds.getMaxX()/table.getClouds().size())-20);
            if (table.getClouds().size()==2)
                img_view.setY(screenBounds.getMaxY()*0.66);
            else
            img_view.setY(screenBounds.getMaxY()-200);
            img_view.setX(pos_x);
            img_view.setPreserveRatio(true);

            root.getChildren().add(img_view);


            pos_x +=(screenBounds.getMaxY()/table.getClouds().size())*1.65;
        }
         **/




        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
