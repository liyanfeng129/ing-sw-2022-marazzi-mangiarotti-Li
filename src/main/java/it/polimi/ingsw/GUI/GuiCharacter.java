package it.polimi.ingsw.GUI;

import it.polimi.ingsw.characterCards2.*;
import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.util.ArrayList;

public class GuiCharacter {
    protected static String red = "Image/student_red.png";
    protected static String yellow = "Image/student_yellow.png";
    protected static String pink = "Image/student_pink.png";
    protected static String blue = "Image/student_blue.png";
    protected static String green = "Image/student_green.png";

    private Game game;
    private AnchorPane root;
    private ArrayList<Node> nodes;
    private AASceneParent aaSceneParent;
    private GUIInfo info;
    private  Label messages;
    private int characterData;


    public GuiCharacter(Game game, AnchorPane root, ArrayList<Node> nodes, AASceneParent aaSceneParent, GUIInfo info, Label messages, int characterData){
        this.game = game;
        this.root = root;
        this.nodes = nodes;
        this.aaSceneParent = aaSceneParent;
        this.info = info;
        this.messages=messages;
        this.characterData = characterData;

    }

    public void showCharacter1(Character1 card, Boolean Action){

        int pos = game.getTable().getCharacters().indexOf(card);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();


        GridPane grid  = new GridPane();
        nodes.add(grid);
        ImageView imgCard = new ImageView(new Image(getClass().getResourceAsStream("Image/CarteTOT_front1.jpg")));
        imgCard.setFitWidth(screenBounds.getMaxY()/8);
        imgCard.setPreserveRatio(true);
        imgCard.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        imgCard.setLayoutY(imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150);
        if(Action){
            nodes.add(imgCard);
            root.getChildren().add(imgCard);
        }

        grid.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        grid.setLayoutY(imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150+screenBounds.getMaxY()/10);
        int[] students = card.getStudents();
        String color;
        int itemPositioned=0;

        for(int i=0;i<5;i++){
            String student =""+i;

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

            for (int j =0;j<students[i];j++) {

                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                img.setFitWidth(screenBounds.getMaxY()/32);
                img.setPreserveRatio(true);
                nodes.add(img);
                if (Action)
                {
                    img.setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            /* drag was detected, start drag-and-drop gesture*/
                            /* allow any transfer mode */
                            Dragboard db = img.startDragAndDrop(TransferMode.ANY);

                            /* put a string on dragboard */
                            ClipboardContent content = new ClipboardContent();

                            content.putString(student);
                            db.setContent(content);
                            event.consume();
                        }
                    });
                }
                img.setOnDragDone(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        event.consume();
                    }
                });

                if (itemPositioned==0)
                    grid.add(img,0,0);
                else if (itemPositioned==1)
                    grid.add(img,0,1);
                else if (itemPositioned==2)
                    grid.add(img,1,0);
                else
                    grid.add(img,1,1);

                itemPositioned++;
            }

        }
        root.getChildren().add(grid);
    }
    public void showCharacter3(Character3 card,Boolean Action){
        int pos = game.getTable().getCharacters().indexOf(card);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        ImageView imgCard = new ImageView(new Image(getClass().getResourceAsStream("Image/CarteTOT_front2.jpg")));
        imgCard.setFitWidth(screenBounds.getMaxY()/8);
        imgCard.setPreserveRatio(true);
        imgCard.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        imgCard.setLayoutY(imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150);
        if(Action){
            nodes.add(imgCard);
            root.getChildren().add(imgCard);
        }
    }
    public void showCharacter5(Character5 card, Boolean Action){
        int pos = game.getTable().getCharacters().indexOf(card);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();


        GridPane grid  = new GridPane();
        nodes.add(grid);
        ImageView imgCard = new ImageView(new Image(getClass().getResourceAsStream("Image/CarteTOT_front4.jpg")));
        imgCard.setFitWidth(screenBounds.getMaxY()/8);
        imgCard.setPreserveRatio(true);
        imgCard.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        imgCard.setLayoutY(imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150);
        if(Action){
            nodes.add(imgCard);
            root.getChildren().add(imgCard);
        }

        grid.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        grid.setLayoutY(imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150+screenBounds.getMaxY()/10);

        ImageView noEntryTyle = new ImageView(new Image(getClass().getResourceAsStream("Image/deny_island_icon.png")));
        noEntryTyle.setFitWidth(screenBounds.getMaxY()/20);
        noEntryTyle.setPreserveRatio(true);
        grid.add(noEntryTyle,0,0);
        grid.add(new Text(""+card.getNo_entry_tile()),1,0);
        nodes.add(grid);
        root.getChildren().add(grid);
    }
    public void showCharacter7(Character7 card, Boolean Action){

        int pos = game.getTable().getCharacters().indexOf(card);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();


        GridPane grid  = new GridPane();
        nodes.add(grid);
        ImageView imgCard = new ImageView(new Image(getClass().getResourceAsStream("Image/CarteTOT_front6.jpg")));
        imgCard.setFitWidth(screenBounds.getMaxY()/8);
        imgCard.setPreserveRatio(true);
        imgCard.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        imgCard.setLayoutY(imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150);
        if(Action){
            nodes.add(imgCard);
            root.getChildren().add(imgCard);
        }

        grid.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        grid.setLayoutY(imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150+screenBounds.getMaxY()/10);
        int[] students = card.getStudents();
        String color;
        int itemPositioned=0;

        for(int i=0;i<5;i++){
            String student =""+i;

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

            for (int j =0;j<card.getCard_students()[i];j++) {

                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                img.setFitWidth(screenBounds.getMaxY()/32);
                img.setPreserveRatio(true);
                nodes.add(img);
                if (Action)
                {
                    img.setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            /* drag was detected, start drag-and-drop gesture*/
                            /* allow any transfer mode */
                            Dragboard db = img.startDragAndDrop(TransferMode.ANY);

                            /* put a string on dragboard */
                            ClipboardContent content = new ClipboardContent();

                            content.putString(student+"C");
                            db.setContent(content);
                            event.consume();
                        }
                    });
                }
                img.setOnDragDone(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        event.consume();
                    }
                });

                if (itemPositioned==0)
                    grid.add(img,0,0);
                else if (itemPositioned==1)
                    grid.add(img,0,1);
                else if (itemPositioned==2)
                    grid.add(img,1,0);
                else if(itemPositioned==3)
                    grid.add(img,1,1);
                else if(itemPositioned==4)
                    grid.add(img,2,0);
                else
                    grid.add(img,2,1);

                itemPositioned++;
            }

        }
        root.getChildren().add(grid);
    }
    public void showCharacter9(Character9 card, Boolean Action){

        int pos = game.getTable().getCharacters().indexOf(card);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        ImageView imgCard = new ImageView(new Image(getClass().getResourceAsStream("Image/CarteTOT_front8.jpg")));
        imgCard.setFitWidth(screenBounds.getMaxY()/8);
        imgCard.setPreserveRatio(true);
        double posY =imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150;
        double posX =screenBounds.getMaxX()-screenBounds.getMaxY()/7.5;

        imgCard.setLayoutX(posX);
        imgCard.setLayoutY(posY);
        if(Action){
            nodes.add(imgCard);
            root.getChildren().add(imgCard);
        }

        chooseColor(posY*1.1,posX*0.8);

    }
    public void showCharacter10(Character10 card,Boolean Action){
        int pos = game.getTable().getCharacters().indexOf(card);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        ImageView imgCard = new ImageView(new Image(getClass().getResourceAsStream("Image/CarteTOT_front9.jpg")));
        imgCard.setFitWidth(screenBounds.getMaxY()/8);
        imgCard.setPreserveRatio(true);
        double posY =imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150;
        double posX =screenBounds.getMaxX()-screenBounds.getMaxY()/7.5;

        imgCard.setLayoutX(posX);
        imgCard.setLayoutY(posY);
        if(Action){
            nodes.add(imgCard);
            root.getChildren().add(imgCard);
        }
    }
    public void showCharacter11(Character11 card,Boolean Action){

        int pos = game.getTable().getCharacters().indexOf(card);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();


        GridPane grid  = new GridPane();
        nodes.add(grid);
        ImageView imgCard = new ImageView(new Image(getClass().getResourceAsStream("Image/CarteTOT_front10.jpg")));
        imgCard.setFitWidth(screenBounds.getMaxY()/8);
        imgCard.setPreserveRatio(true);
        imgCard.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        imgCard.setLayoutY(imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150);
        if(Action){
            nodes.add(imgCard);
            root.getChildren().add(imgCard);
        }
        double posx = screenBounds.getMaxX()-screenBounds.getMaxY()/7.5;
        double posy = imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150+screenBounds.getMaxY()/10;
        grid.setLayoutX(posx);
        grid.setLayoutY(posy);
        int[] students = card.getStudents();
        String color;
        int itemPositioned=0;

        for(int i=0;i<5;i++){
            String student =""+i;

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

            for (int j =0;j<students[i];j++) {

                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                img.setFitWidth(screenBounds.getMaxY()/32);
                img.setPreserveRatio(true);
                Button button = new Button();
                button.setGraphic(img);
                button.setMaxWidth(screenBounds.getMaxY()/32);
                button.setStyle("-fx-border-color:transparent;");
                button.setStyle("-fx-background-color:transparent;");

                nodes.add(button);
                if (Action)
                {
                    final int studentFinal = i;
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            ArrayList<Object> inputs = new ArrayList<>();
                            inputs.add(studentFinal);
                            System.out.println(studentFinal);
                            /**TODO YANFENG input characte 11
                             */
                            Command command = game.getLastCommand();
                            String msg = " ";
                            try {
                                msg = command.GUIGetData(inputs);
                            } catch (EriantysExceptions ex) {
                                ex.printStackTrace();
                            }
                            if (msg.equals(Config.GUI_COMMAND_GETDATA_SUC)) {
                                info.setCommand(command);
                                Platform.runLater(() -> new GuiMessageSender(aaSceneParent, Config.COMMAND_EXECUTE).run());
                            } else
                                messages.setText(msg);

                        }
                    });
                }

                if (itemPositioned==0)
                    grid.add(button,0,0);
                else if (itemPositioned==1)
                    grid.add(button,0,1);
                else if (itemPositioned==2)
                    grid.add(button,1,0);
                else
                    grid.add(button,1,1);

                itemPositioned++;
            }

        }
        root.getChildren().add(grid);
    }
    public void showCharacter12(Character12 card,Boolean Action){

        int pos = game.getTable().getCharacters().indexOf(card);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        ImageView imgCard = new ImageView(new Image(getClass().getResourceAsStream("Image/CarteTOT_front11.jpg")));
        imgCard.setFitWidth(screenBounds.getMaxY()/8);
        imgCard.setPreserveRatio(true);
        double posY =imgCard.getLayoutBounds().getHeight()*pos+screenBounds.getMaxY()/150;
        double posX =screenBounds.getMaxX()-screenBounds.getMaxY()/7.5;

        imgCard.setLayoutX(posX);
        imgCard.setLayoutY(posY);
        if(Action){
            nodes.add(imgCard);
            root.getChildren().add(imgCard);
        }

        chooseColor(posY*1.3,posX*0.8);

    }

    public void chooseColor(double positionY,double positionX){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        GridPane grid = new GridPane();
        String color;


        for (int i=0; i<5;i++){
            Button student = new Button();
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
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
            img.setFitWidth(screenBounds.getMaxY()/32);
            img.setPreserveRatio(true);
            student.setMaxWidth(screenBounds.getMaxY()/32);
            student.setStyle("-fx-border-color:transparent;");
            student.setStyle("-fx-background-color:transparent;");
            student.setGraphic(img);
            nodes.add(student);

            final int studentFinal = i;
            student.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {



                    if(characterData==9) {


                        ArrayList<Object> inputs = new ArrayList<>();
                        inputs.add(studentFinal);
                        /**TODO YANFENG input characte 9
                         */
                        Command command = game.getLastCommand();
                        String msg = " ";
                        try {
                            msg = command.GUIGetData(inputs);
                        } catch (EriantysExceptions ex) {
                            ex.printStackTrace();
                        }
                        if (msg.equals(Config.GUI_COMMAND_GETDATA_SUC)) {
                            info.setCommand(command);
                            Platform.runLater(() -> new GuiMessageSender(aaSceneParent, Config.COMMAND_EXECUTE).run());
                        } else
                            messages.setText(msg);
                    }
                    else{
                        ArrayList<Object> inputs = new ArrayList<>();
                        inputs.add(studentFinal);
                        /**TODO YANFENG input characte 12
                         */
                        Command command = game.getLastCommand();
                        String msg = " ";
                        try {
                            msg = command.GUIGetData(inputs);
                        } catch (EriantysExceptions ex) {
                            ex.printStackTrace();
                        }
                        if (msg.equals(Config.GUI_COMMAND_GETDATA_SUC)) {
                            info.setCommand(command);
                            Platform.runLater(() -> new GuiMessageSender(aaSceneParent, Config.COMMAND_EXECUTE).run());
                        } else
                            messages.setText(msg);

                    }
                }


            });
            grid.add(student,i,0);
        }

        VBox vbox =new VBox();
        nodes.add(vbox);
        vbox.setLayoutX(positionX);
        vbox.setLayoutY(positionY);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(new Text("click a student to select the student color"));
        root.getChildren().add(vbox);
    }

}
