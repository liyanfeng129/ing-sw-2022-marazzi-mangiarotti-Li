package it.polimi.ingsw.GUI;

import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.Config.UPDATE;

public class GameBoardController extends AASceneParent {

    @FXML
    private Label messages;

    private Game game;

    @FXML
    private AnchorPane root;
    protected static String red = "Image/student_red.png";
    protected static String yellow = "Image/student_yellow.png";
    protected static String blue = "Image/student_blue.png";
    protected static String green = "Image/student_green.png";
    protected static String pink = "Image/student_pink.png";
    protected static String color;


    private CharacterCard cardChoice;
    private List<Node> nodes= new ArrayList<>();

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



    public void setGame (Game game){
        this.game = game;
    }







    @FXML
    private void initialize(){

        Platform.runLater(new Runnable() {
            @Override public void run() {

                try {
                    updtadeGame();
                    updateCaracter();

                } catch (EriantysExceptions e) {
                    e.printStackTrace();
                }
            }
        });


    }




    @FXML
    protected void update(ActionEvent event)throws EriantysExceptions {
        removeGame();
        game.getTable().getIslands().remove(game.getTable().getIsland(0));
        game.getTable().getIsland(1).setMotherNature(true);
        game.getTable().getIsland(5).addStudent(2);
        updtadeGame();


    }





    public void updtadeGame() throws EriantysExceptions {
        update_islands();
        update_clouds();
        updateDiningRoom();
        updateWaitingRoom();
        updateProfessor();
    }


    public void update_islands(){
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
            nodes.add(img_view);
            root.getChildren().add(img_view);

            GridPane(pos_x,pos_y,game,i);

            if (table.getIslands().get(i).getMotherNature()){
                ImageView MN_view = new ImageView(new Image(getClass().getResourceAsStream("image/mother_nature.png")));
                MN_view.setFitWidth(50);
                MN_view.setFitHeight(50);
                MN_view.setPreserveRatio(true);
                MN_view.setLayoutX(pos_x+35);
                MN_view.setLayoutY(pos_y+30-5);
                nodes.add(MN_view);
                root.getChildren().add(MN_view);
            }
        }

    }
    public void update_clouds(){
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
            nodes.add(img_view);
            pos_x +=100;
        }

    }
    public void updateDiningRoom(){
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
                nodes.add(img);
                img.setFitWidth(27);
                img.setFitHeight(27);
                img.setPreserveRatio(true);
                img.setLayoutX(pos_x + j*35);
                img.setLayoutY(pos_y);
                root.getChildren().add(img);

            }
        }


    }
    public void updateWaitingRoom(){
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
                    nodes.add(img);
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
    public void updateProfessor(){
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
                nodes.add(img);
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
    public void updateCaracter() throws EriantysExceptions{

       List<CharacterCard> cards = game.getTable().getCharacters();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_max_x = screenBounds.getMaxX();
        double pos_y =0;

        for(int i =0;i<3; i++){
           CharacterCard card = cards.get(i);
           String img_file;
            int c = card.getN_card();
            System.out.println(c);
           switch (c) {
               case 1:
                   img_file = "Image/CarteTOT_front1.jpg";break;
               case 2:
                   img_file = "Image/CarteTOT_front2.jpg";break;

               case 3:
                   img_file = "Image/CarteTOT_front3.jpg";break;
               case 4:
                   img_file = "Image/CarteTOT_front4.jpg";break;
               case 5:
                   img_file = "Image/CarteTOT_front5.jpg";break;
               case 6:
                   img_file = "Image/CarteTOT_front6.jpg";break;
               case 7:
                   img_file = "Image/CarteTOT_front7.jpg";break;
               case 8:
                   img_file = "Image/CarteTOT_front8.jpg";break;
               case 9:
                   img_file = "Image/CarteTOT_front9.jpg";break;
               case 10:
                   img_file = "Image/CarteTOT_front10.jpg";break;
               case 11:
                   img_file = "Image/CarteTOT_front11.jpg";break;
               default:
                   img_file = "Image/CarteTOT_front12.jpg";break;
           }
           ImageView img = new ImageView(new Image(getClass().getResourceAsStream(img_file)));
           Button bt = new Button();
           img.setFitWidth(100);
           img.setPreserveRatio(true);
           bt.setLayoutX(pos_max_x-112);
           bt.setPadding(new Insets(0.5,0.5,0.5,0.5));
           if (i==0)
                pos_y = 5;
           else
              pos_y =pos_y + (img.getLayoutBounds().getHeight());

           System.out.println(pos_y);
           bt.setLayoutY(pos_y);
           bt.setGraphic(img);
           bt.setOnAction(new EventHandler<ActionEvent>() {
               @Override public void handle(ActionEvent e) {
                   cardChoice = card;

                   messages.setText(card.getMsg());
               }
           });
           pos_y = pos_y+5;




           root.getChildren().add(bt);
       }

    }





    private void removeGame() {
        for(int i=0; i<nodes.size();i++){
            Node img = nodes.get(i);
            root.getChildren().remove(img);
        }
        nodes.clear();

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
    protected void ShowAssistant(ActionEvent event){
        this.messages.setText("ShowAssistant");
    }
    protected void Action(ActionEvent event){

        this.messages.setText("ShowAction");
    }
    protected  void GridPane(double pos_x,double pos_y,Game game,int i){

        ImageView red_student = new ImageView(new Image(getClass().getResourceAsStream(red)));
        ImageView yellow_student = new ImageView(new Image(getClass().getResourceAsStream(yellow)));
        ImageView blue_student = new ImageView(new Image(getClass().getResourceAsStream(blue)));
        ImageView green_student = new ImageView(new Image(getClass().getResourceAsStream(green)));
        ImageView pink_student = new ImageView(new Image(getClass().getResourceAsStream(pink)));
        GridPane island_student = new GridPane();
        //island_student.setMaxSize(10,10);
        //island_student.setPreserveRatio(true);
        //island_student.setAlignment(Pos.CENTER);
        //grandezza studenti
        red_student.setFitWidth(15);
        red_student.setFitHeight(15);
        yellow_student.setFitWidth(15);
        yellow_student.setFitHeight(15);
        blue_student.setFitWidth(15);
        blue_student.setFitHeight(15);
        green_student.setFitWidth(15);
        green_student.setFitHeight(15);
        pink_student.setFitWidth(15);
        pink_student.setFitHeight(15);
        island_student.setHgap(5);
        island_student.setVgap(2);
        island_student.setLayoutX(pos_x+70);
        island_student.setLayoutY(pos_y+60);
        island_student.add(red_student, 0, 0);
        island_student.add(new Text(""+game.getTable().getIsland(i).getStudents()[0]), 1, 0);
        island_student.add(yellow_student, 0, 1);
        island_student.add(new Text(""+game.getTable().getIsland(i).getStudents()[1]), 1, 1);
        island_student.add(blue_student, 0, 2);
        island_student.add(new Text(""+game.getTable().getIsland(i).getStudents()[2]), 1, 2);
        island_student.add(green_student, 0, 3);
        island_student.add(new Text(""+game.getTable().getIsland(i).getStudents()[3]), 1, 3);
        island_student.add(pink_student, 0, 4);
        island_student.add(new Text(""+game.getTable().getIsland(i).getStudents()[4]), 1, 4);
        //island_student.setGridLinesVisible (true);



        nodes.add(island_student);
        root.getChildren().add(island_student);
    }



}