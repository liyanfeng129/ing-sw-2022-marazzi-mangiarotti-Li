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
    private List<ImageView> Clouds = new ArrayList<>();
    private List<ImageView> WaitingRoom = new ArrayList<>();
    private ImageView[][] DiningRoom = new ImageView[10][5];
    private List<ImageView> Professor = new ArrayList<>();
    /** TODO
     * rimuovere leo
     *
     * far passare il nome tra le scene
     *
     * prendere il game in update
     *
     * rimuovere il test in gameSetUpController
     */
    private String name = "leo";



    public List<ImageView> getIslands() {
        return Islands;
    }








    @FXML
    private void initialize() throws EriantysExceptions {

        Platform.runLater(new Runnable() {
            @Override public void run() {

                try {
                    updtadeGame();
                } catch (EriantysExceptions e) {
                    e.printStackTrace();
                }
            }
        });


    }




    @FXML
    protected void update(ActionEvent event) throws IOException, EriantysExceptions {
        removeGame();
        //  qui bisogna passargli il nuovo game
        updtadeGame();


    }

    public void setGame (Game game){
        this.game = game;
    }

    public void updtadeGame() throws EriantysExceptions {
        update_islands();
        update_clouds();
        updateDiningRoom();
        updateWaitingRoom();
        updateProfessor();
    }


    public void update_islands() throws EriantysExceptions{
        Table table = game.getTable();


        int pos_x_center =450; //650
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
        int pos_x_center =450;//650
        int pos_y_center =195;
        double pos_x ;

        pos_x =pos_x_center - 100 ;
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
            Clouds.add(img_view);
            pos_x +=100;
        }

    }
    public void updateDiningRoom() throws EriantysExceptions    {
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
                if (i==3) {
                    color = blue;
                    pos_y = screenBounds.getHeight()-71;
                }
                else if (i==2) {
                    color = pink;
                    pos_y = screenBounds.getHeight()-122;
                }
                else if (i==1) {
                    color = yellow;
                    pos_y = screenBounds.getHeight()-175;
                }
                else if (i==0) {
                    color = red;
                    pos_y = screenBounds.getHeight()-227;
                }
                else {
                    color = green;
                    pos_y = screenBounds.getHeight()-279;
                }
                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                DiningRoom[j][i]=img;
                img.setFitWidth(27);
                img.setFitHeight(27);
                img.setPreserveRatio(true);
                img.setLayoutX(pos_x + j*35);
                img.setLayoutY(pos_y);
                root.getChildren().add(img);

            }
        }


    }
    public void updateWaitingRoom() throws EriantysExceptions{
        String red = "Image/student_red.png";
        String yellow = "Image/student_yellow.png";
        String blue = "Image/student_blue.png";
        String green = "Image/student_green.png";
        String pink = "Image/student_pink.png";
        String color;

        Player player = game.getPlayers().stream().filter(p -> p.getName()==name).collect(Collectors.toList()).get(0);
        PlayerBoard pb = player.getPlayerBoard();
        int[] waitingRoom = pb.getWaitingRoom();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_x ;
        double pos_y = screenBounds.getHeight()-70;
        int cont =0;
        for(int i =0; i<5;i++){
                if (i==3)
                    color = blue;
                else if (i==2)
                    color = pink;
                else if (i==1)
                    color = yellow;
                else if (i==0)
                    color = red;
                else
                    color = green;
                for (int j =0;j<waitingRoom[i];j++) {
                    ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                    WaitingRoom.add(img);
                    img.setFitWidth(27);
                    img.setFitHeight(27);
                    img.setPreserveRatio(true);

                    if (cont==0){
                        pos_x = 70;
                        pos_y = screenBounds.getHeight()-278;
                        img.setLayoutX(pos_x);
                        img.setLayoutY(pos_y);
                        cont +=1;
                    }
                    else if(cont<5) {
                        pos_x = 70;
                        img.setLayoutX(pos_x);
                        img.setLayoutY(pos_y+52*(cont));
                        cont +=1;
                    }
                    else {
                        if (cont==5)
                            pos_y = screenBounds.getHeight()-225;

                        pos_x = 27;
                        img.setLayoutX(pos_x);
                        img.setLayoutY(pos_y+52*(cont-5));
                        cont +=1;
                    }

                    root.getChildren().add(img);

                }


            }
        }
    public void updateProfessor() throws EriantysExceptions{
        String red = "Image/teacher_red.png";
        String yellow = "Image/teacher_yellow.png";
        String blue = "Image/teacher_blue.png";
        String green = "Image/teacher_green.png";
        String pink = "Image/teacher_pink.png";
        String color;

        Player player = game.getPlayers().stream().filter(p -> p.getName()==name).collect(Collectors.toList()).get(0);
        Mage mage = player.getMage();
        Mage[] prof = game.getProfessors().getList_professors();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_x= 520;
        double pos_y ;
        for(int i =0; i<5;i++){
            if (i==3) {
                color = blue;
                pos_y = screenBounds.getHeight()-74;
            }
            else if (i==2) {
                color = pink;
                pos_y = screenBounds.getHeight()-126;
            }
            else if (i==1) {
                color = yellow;
                pos_y = screenBounds.getHeight()-178;
            }
            else if (i==0) {
                color = red;
                pos_y = screenBounds.getHeight()-230;
            }
            else {
                color = green;
                pos_y = screenBounds.getHeight()-282;
            }
            if (prof[i]==mage) {
                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                Professor.add(img);
                img.setFitWidth(35);
                img.setFitHeight(35);
                img.setPreserveRatio(true);
                img.setRotate(30);
                img.setLayoutX(pos_x);
                img.setLayoutY(pos_y);
                root.getChildren().add(img);
            }


        }
    }

    public void removeGame() throws EriantysExceptions{
        remove_island();
        remove_DiningRoom();
        remove_WaitingRoom();
        remove_Clouds();
        remove_Professors();
    }


    private void remove_island() {
        Table table = game.getTable();
        for(int i=0; i<table.getIslands().size();i++){
            ImageView img = getIslands().get(i);
            root.getChildren().remove(img);
        }
        getIslands().clear();

    }
    private void remove_DiningRoom(){
        Player player = game.getPlayers().stream().filter(p -> p.getName()==name).collect(Collectors.toList()).get(0);
        PlayerBoard pb = player.getPlayerBoard();
        for(int i=0;i<5;i++)
            for(int j=0;j<pb.getDiningRoom()[i];j++) {
                root.getChildren().remove(DiningRoom[j][i]);
            }
        DiningRoom = new ImageView[10][5];
    }
    private void remove_WaitingRoom() {
        for(int i=0; i<WaitingRoom.size();i++){
            ImageView img = WaitingRoom.get(i);
            root.getChildren().remove(img);
        }
        WaitingRoom.clear();

    }
    private void remove_Clouds() {
        for(int i=0; i<Clouds.size();i++){
            ImageView img = Clouds.get(i);
            root.getChildren().remove(img);
        }
        Clouds.clear();

    }
    private void remove_Professors() {
        for(int i=0; i<Professor.size();i++){
            ImageView img = Professor.get(i);
            root.getChildren().remove(img);
        }
        Professor.clear();

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
    protected void Action(ActionEvent event) throws IOException {

        this.messages.setText("ShowAction");
    }



}