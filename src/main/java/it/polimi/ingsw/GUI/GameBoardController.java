package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Table;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameBoardController extends AASceneParent {

    @FXML
    private Label messages;

    private Game game;

    @FXML
    private AnchorPane root;





    @FXML
    private void initialize() throws EriantysExceptions {

        Platform.runLater(new Runnable() {
            @Override public void run() {

                try {
                    showGame();
                } catch (EriantysExceptions e) {
                    e.printStackTrace();
                }
            }
        });


    }


    public void showGame() throws EriantysExceptions {
        Table table = game.getTable();


        int pos_x_center =650;
        int pos_y_center =195;
        double pos_x ;
        double pos_y ;
        int r = 400;
        double angle = 360/table.getIslands().size();
        double angle_;
        for(int i=0; i<table.getIslands().size();i++){
            ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream("image/island1.png")));
            img_view.setFitWidth(150);
            img_view.setFitHeight(150);
            img_view.setPreserveRatio(true);

            angle_ = angle*i;
            if (angle_==0 || angle_ == 180)
                if (angle_==0)
                    pos_x = -r*Math.cos(Math.toRadians(angle_))+pos_x_center - 40;
                else
                    pos_x = -r*Math.cos(Math.toRadians(angle_))+pos_x_center + 40;
            else
                pos_x = -r*Math.cos(Math.toRadians(angle_))+pos_x_center;
            pos_y =-r*Math.sin(Math.toRadians(angle_))*0.50+pos_y_center;
            img_view.setLayoutX(pos_x);
            img_view.setLayoutY(pos_y+30);

            root.getChildren().add(img_view);
        }

        pos_x =650 - 100 ;
        String img;
        for(int i=0; i<table.getClouds().size();i++){
            if (table.getClouds().size()==2)
                img = "image/cloud_card_1.png";
            else
                img = "image/cloud_card.png";
            ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream(img)));
            img_view.setFitWidth(90);
            img_view.setFitHeight(90);
            img_view.setPreserveRatio(true);
            //button.setStyle("
            // -fx-background-color: white");
            img_view.setLayoutX(pos_x);
            img_view.setLayoutY(pos_y_center+30);

            root.getChildren().add(img_view);
            pos_x +=100;
        }
    }

    @FXML
    protected void update(ActionEvent event) throws IOException {
        this.game.getTable().getIslands().remove(this.game.getTable().getIsland(0));


        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("GameBoard.fxml"));
        root=loader.load();

        GameBoardController controller = loader.getController();

        controller.setGame(game);

        switchScene(root, event);

    }

    public void setGame (Game game){
        this.game = game;
    }












    @FXML
    protected void ShowCaracter(ActionEvent event) {
        this.messages.setText("ShowCaracter");
    }
    @FXML
    protected void ShowOpponentBoard(ActionEvent event) {
        this.messages.setText("ShowOpponentBoard");
    }

    @FXML
    protected void ShowAssistant(ActionEvent event) throws IOException {
        this.messages.setText("ShowAssistant");
    }



}