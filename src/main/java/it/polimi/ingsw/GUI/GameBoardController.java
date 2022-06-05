package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameBoardController extends AASceneParent {

    @FXML
    private Label messages;

    private Game game;

    @FXML
    private AnchorPane root;


    private List<ImageView> Islands = new ArrayList<>();
    private String name = "leo";

    public List<ImageView> getIslands() {
        return Islands;
    }

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

    private void remove_island() {
        Table table = game.getTable();
        for(int i=0; i<table.getIslands().size();i++){
            ImageView img = getIslands().get(i);
            System.out.println(getIslands().size());
            root.getChildren().remove(img);
        }
        getIslands().clear();

    }


    @FXML
    protected void update(ActionEvent event) throws IOException, EriantysExceptions {
        remove_island();
        this.game.getTable().getIslands().remove(this.game.getTable().getIsland(0));
        showGame();


    }

    public void setGame (Game game){
        this.game = game;
    }

    public void showGame() throws EriantysExceptions {
        update_islands();
        update_clouds();
        update_board();
    }


    public void update_islands() throws EriantysExceptions{
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
            getIslands().add(img_view);
            root.getChildren().add(img_view);
        }

    }
    public void update_clouds() throws EriantysExceptions{
        Table table = game.getTable();
        int pos_x_center =650;
        int pos_y_center =195;
        double pos_x ;
        double pos_y ;

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
    public void update_board() throws EriantysExceptions{
        String red = "Image/student_red.png";
        String yellow = "Image/student_yellow.png";
        String blue = "Image/student_blue.png";
        String green = "Image/student_green.png";
        String pink = "Image/student_pink.png";
        String color;

        Player player = game.getPlayers().stream().filter(p -> p.getName()==name).collect(Collectors.toList()).get(0);
        PlayerBoard pb = player.getPlayerBoard();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_x = 139;
        double pos_y = screenBounds.getHeight()-70;
        for(int i =0; i<5;i++){
            for(int j=0; j<pb.getDiningRoom()[i];j++){
                if (i==0)
                    color = blue;
                else if (i==1)
                    color = pink;
                else if (i==2)
                    color = yellow;
                else if (i==3)
                    color = red;
                else
                    color = green;
                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                img.setFitWidth(27);
                img.setFitHeight(27);
                img.setPreserveRatio(true);
                img.setLayoutX(pos_x + j*35);
                img.setLayoutY(pos_y - i*52);
                root.getChildren().add(img);

            }
        }


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