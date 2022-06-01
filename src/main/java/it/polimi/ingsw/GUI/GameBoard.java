package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Table;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameBoard extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene (root);
        stage.setFullScreen(true);
        stage.setTitle("Eryantis");
        stage.setScene(scene);
        




        Table table  = new Table();
        for(int i = 0; i < 3; i++){
            Cloud cloud = new Cloud();
            cloud.setCloud(3);
            table.getClouds().add(cloud);
        }






        int pos_x_center =650;
        int pos_y_center =195;
        double pos_x ;
        double pos_y ;
        int r = 400;
        double angle = 360/table.getIslands().size();
        double angle_;
        System.out.println("angle: "+angle);
        for(int i=0; i<table.getIslands().size();i++){
            ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream("island1.png")));
            img_view.setFitWidth(120);
            img_view.setFitHeight(120);
            img_view.setPreserveRatio(true);
            Button button = new Button( ""+i,img_view);
            button.setStyle("-fx-background-radius: 500");
            button.setPadding(new Insets(0,0,0,0));
            button.setMaxSize(120,120);

            angle_ = angle*i;
            System.out.println("angle: "+angle_);
            if (angle_==0 || angle_ == 180)
                if (angle_==0)
                    pos_x = -r*Math.cos(Math.toRadians(angle_))+pos_x_center - 40;
                else
                    pos_x = -r*Math.cos(Math.toRadians(angle_))+pos_x_center + 40;
            else
                pos_x = -r*Math.cos(Math.toRadians(angle_))+pos_x_center;
            pos_y =-r*Math.sin(Math.toRadians(angle_))*0.50+pos_y_center;
            System.out.println("pos_x: "+pos_x);
            System.out.println("pos_y: "+pos_y);
            button.setLayoutX(pos_x);
            button.setLayoutY(pos_y);

            root.getChildren().add(button);
        }


        pos_x =650 - 100 ;
        String img;
        for(int i=0; i<table.getClouds().size();i++){
            if (table.getClouds().size()==2)
                img = "cloud_card_1.png";
            else
                img = "cloud_card.png";
            ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream(img)));
            img_view.setFitWidth(90);
            img_view.setFitHeight(90);
            img_view.setPreserveRatio(true);
            Button button = new Button( ""+i,img_view);
            //button.setStyle("-fx-background-color: white");
            button.setPadding(new Insets(0,0,0,0));
            button.setMaxSize(90,90);
            button.setLayoutX(pos_x);
            button.setLayoutY(pos_y_center+30);

            root.getChildren().add(button);
            pos_x +=100;
        }



        pos_x =0;
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        for(int i=0; i<table.getClouds().size();i++){

            ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream("PLANCIA_GIOCO.png")));
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













        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
