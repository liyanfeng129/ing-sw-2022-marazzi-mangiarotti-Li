package it.polimi.ingsw.GUI;

import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AAA extends AASceneParent {

    private Label messages = new Label();

    private Game game;
    private Cloud cloudChoice;
    private Island islandChoice;
    private Assistant assistantChoice;


    @FXML
    private AnchorPane root;
    protected static String red = "Image/student_red.png";
    protected static String yellow = "Image/student_yellow.png";
    protected static String blue = "Image/student_blue.png";
    protected static String green = "Image/student_green.png";
    protected static String pink = "Image/student_pink.png";


    private CharacterCard cardChoice = null;
    private List<Node> nodes= new ArrayList<>();
    private List<Node> board= new ArrayList<>();

    private String name="leo";
    private String board_name = name;


    public void setGame (Game game){
        this.game = game;
    }


    @FXML
    private void initialize(){

        Platform.runLater(new Runnable() {
            @Override public void run() {
                String fase = "endgame";
                try {
                    showGame(fase);

                } catch (EriantysExceptions e) {
                    e.printStackTrace();
                }
            }
        });


    }





    protected void update()throws EriantysExceptions {
        removeGame();

        /**TODO YANFENG
         * assegnare game aggiornato
         *
         */

        game.getTable().getIslands().remove(game.getTable().getIsland(0));
        game.getTable().getIsland(1).setMotherNature(true);
        game.getTable().getClouds().get(0).setCloudStudent(new int[]{1,0,2,0,0});
        game.getTable().getClouds().get(1).setCloudStudent(new int[]{1,1,1,0,0});
        game.getTable().getClouds().get(2).setCloudStudent(new int[]{2,1,0,0,0});
        game.getTable().getIsland(game.getTable().getIslands().size()/2).addStudent(2);
        game.getTable().getIsland(game.getTable().getIslands().size()/3).addStudent(1);
        game.getTable().getIsland(game.getTable().getIslands().size()/3).addStudent(3);
        game.getTable().getIsland(5).setTower(TowerColor.BLACK);
        game.getTable().getIsland(3).setTower(TowerColor.WHITE);
        game.getTable().getIsland(7).setTower(TowerColor.GREY);
        /**TODO YANFENG
         * in base al turno del giocatore puoi fare
         *
         *  showGameNoAction() è per il solo aggiornamento (chi non sta giocando )
         *
         * gli altri sono per ci gioca
         *
         * showGameDragStudent per muovere studente o selezionanre la carta
         *
         * showGameMoveMN per muovere MN o selezionare carta
         *
         * showGamePickCloud per prendere cloud o selezionare carta
         *
         * se vuoi anche mandare dei messaggi fai
         *
         * messages.setText("msg") dopo aver fatto queste cose
         *
         * showAssistant(); per far vedere gli assitenti
         *
         * devi togliere lo showGameNoAction qui sotto ricordatelo
         */
        showGameNoAction();


    }

    /**
     *
     * @param fase può essese di 4 tipi:
     *             1) Assistants: per far vedere gli assistenti
     *             2) updateGame
     *             3) MoveSudents
     *             4) MoveMN
     *             5) SelectCloud
     */


    public void showGame(String fase) throws EriantysExceptions {

        switch (fase) {
            case "Assistants":
                showAssistant(); break;
            case "updateGame":
                showGameNoAction();break;
            case "MoveSudents":
                showGameDragStudent();break;
            case "MoveMN":
                showGameMoveMN();break;
            case "SelectCloud":
                showGamePickCloud();break;
            case "endgame":
                endGame();break;
        }
    }

    public void showGameNoAction() throws EriantysExceptions {
        switcBoardController(false);
        showTowers();
        showProfessors();
        showIslands(false);
        showClouds(false);
        showDiningRoom();
        showWaitingRoom(false);
        if (game.isExpertMode()) {
            showCharacter();
            showWallet();
        }
    }

    public void showGameDragStudent() throws EriantysExceptions {


        switcBoardController(true);
        showTowers();
        showProfessors();
        showIslands(false);
        addButtonCharacter();



        showClouds(false);
        showDiningRoom();
        showWaitingRoom(true);
        if (game.isExpertMode()) {
            showCharacter();
            showWallet();
        }
    }

    public void showGameMoveMN() throws EriantysExceptions {


        switcBoardController(false);
        addButtonCharacter();
        showTowers();
        showIslands(true);
        showClouds(false);
        showDiningRoom();
        showWaitingRoom(false);
        showProfessors();
        if (game.isExpertMode()) {
            showCharacter();
            showWallet();
        }
    }

    public void showGamePickCloud() throws EriantysExceptions {

        switcBoardController(false);
        addButtonCharacter();
        showTowers();

        showIslands(false);

        showClouds(true);
        showDiningRoom();
        showWaitingRoom(false);
        showProfessors();
        if (game.isExpertMode()) {
            showCharacter();
            showWallet();
        }
    }

    public void endGame(){
        /**TODO LEO
         * aggiungre la schermata di fine partita
         */
        removeGame();
    }

    // tavolo gioco
    public void showIslands(Boolean Action){
        Table table = game.getTable();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_x_center =screenBounds.getMaxX()*2/7 +20; //650
        double pos_y_center =screenBounds.getMaxY()/5;
        double pos_x ;
        double pos_y ;
        double r = screenBounds.getMaxY()/(2.25);
        double angle = 360/table.getIslands().size();
        double angle_;
        for(int i=0; i<table.getIslands().size();i++){
            ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream("image/island1.png")));

            img_view.setFitWidth(screenBounds.getMaxY()/6);
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
            Button bt = new Button();
            bt.setLayoutX(pos_x);
            bt.setLayoutY(pos_y+30);
            bt.setGraphic(img_view);
            bt.setBackground(null);

            ImageView imgDragDrop = new ImageView(new Image(getClass().getResourceAsStream("image/island1.png")));
            imgDragDrop.setFitWidth(screenBounds.getMaxY()/6);
            imgDragDrop.setPreserveRatio(true);

            imgDragDrop.setLayoutX(pos_x);
            imgDragDrop.setLayoutY(pos_y+30);


            if (Action){
                Island islandSelected = game.getTable().getIsland(i);
                bt.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        islandChoice = islandSelected;
                        /**TODO YANFENG
                         * in islandChoice trovi l'isola scelta pe muovere madre natura
                         */
                        messages.setText("hai selezionnato l'isola");
                    }
                });

            }
            else{
                imgDragDrop.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        //data is dragged over the target
                        System.out.println("onDragOver");

                        // accept it only if it is  not dragged from the same node
                        //( and if it has a string data
                        if (event.getGestureSource() != imgDragDrop &&
                                event.getDragboard().hasString()) {
                            // allow for both copying and moving, whatever user chooses
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }

                        event.consume();
                    }
                });

                imgDragDrop.setOnDragEntered(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture entered the target */
                        System.out.println("onDragEntered");
                        /* show to the user that it is an actual gesture target */
                        if (event.getGestureSource() != imgDragDrop &&
                                event.getDragboard().hasString()) {
                            System.out.println("setOnDragEntered");
                        }

                        event.consume();
                    }
                });

                imgDragDrop.setOnDragDropped(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data dropped */
                        System.out.println("onDragDropped");
                        /* if there is a string data on dragboard, read it and use it */
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            System.out.println("setOnDragDropped");
                            /**TODO YANFENG
                             * qui puoi prendere l'isola su cui si è trascinnato uno studente e lo studente
                             */
                            success = true;
                        }
                        /* let the source know whether the string was successfully
                         * transferred and used */
                        event.setDropCompleted(success);

                        event.consume();
                    }
                });
            }


            nodes.add(bt);
            root.getChildren().add(bt);

            /** Adds students to island */
            GridPane(pos_x,pos_y,game.getTable().getIslands().get(i).getStudents());

            /** Adds towers and size */
            int size = game.getTable().getIslands().get(i).getSize();
            if(game.getTable().getIslands().get(i).getTower()!=null) {
                TowerColor color = game.getTable().getIslands().get(i).getTower();
                TowerGridPane(pos_x,pos_y,size,color);
            }
            else
                TowerGridPane(pos_x,pos_y,size,null);

            /** Adds no entry tiles*/
            if (game.getTable().getIslands().get(i).isNoEntryTiles())
                noTileImg(pos_x,pos_y);

            /** Adds mother nature*/
            if (table.getIslands().get(i).getMotherNature()){
                ImageView MN_view = new ImageView(new Image(getClass().getResourceAsStream("image/mother_nature.png")));
                MN_view.setFitWidth(screenBounds.getMaxY()/18);
                MN_view.setFitHeight(screenBounds.getMaxY()/18);
                MN_view.setPreserveRatio(true);
                MN_view.setLayoutX(pos_x+screenBounds.getMaxY()/25.7);
                MN_view
                        .setLayoutY(pos_y+screenBounds.getMaxY()/36);
                nodes.add(MN_view);
                root.getChildren().add(MN_view);
            }

            if (!Action){
                imgDragDrop.setOpacity(0);
                nodes.add(imgDragDrop);
                root.getChildren().add(imgDragDrop);
            }
        }
    }
    public void showClouds(Boolean Action){
        Table table = game.getTable();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_x_center =screenBounds.getMaxX()*2/7 +20; //650
        double pos_y_center =screenBounds.getMaxY()/5;
        double pos_y = pos_y_center+30;
        double pos_x ;
        pos_x =pos_x_center - screenBounds.getMaxY()/6 ;
        String img;
        for(int i=0; i<table.getClouds().size();i++){
            Node node;
            if (table.getClouds().size()==2)
                img = "image/cloud_card_1.png";
            else
                img = "image/cloud_card.png";
            ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream(img)));
            img_view.setFitWidth(screenBounds.getMaxY()/6);
            img_view.setPreserveRatio(true);
            img_view.setLayoutX(pos_x);
            img_view.setLayoutY(pos_y);
            node = img_view;

            if(Action) {
                Button bt = new Button();
                bt.setGraphic(img_view);
                bt.setBackground(null);
                Cloud cloudPicked = game.getTable().getClouds().get(i);
                bt.setLayoutX(pos_x);
                bt.setLayoutY(pos_y);
                bt.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        cloudChoice = cloudPicked;
                        /**TODO YANFENG
                         * in cloudIndex trovi la posizione dell'isola
                         */
                    }
                });
                node = bt;
            }
            root.getChildren().add(node);

            GridPane(pos_x,pos_y-20,game.getTable().getClouds().get(i).getStudents());

            nodes.add(node);
            pos_x +=screenBounds.getMaxY()/6;
        }
    }
    public void showCharacter(){

        List<CharacterCard> cards = game.getTable().getCharacters();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_max_x = screenBounds.getMaxX();
        double pos_y =0;

        for(int i =0;i<3; i++){
            CharacterCard card = cards.get(i);
            String img_file;
            int c = card.getN_card();
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
            img.setFitWidth(screenBounds.getMaxY()/8);
            img.setPreserveRatio(true);
            bt.setLayoutX(pos_max_x-screenBounds.getMaxY()/7.5);
            bt.setPadding(new Insets(0.5,0.5,0.5,0.5));
            if (i==0)
                pos_y = screenBounds.getMaxY()/180;
            else
                pos_y =pos_y + (img.getLayoutBounds().getHeight());

            bt.setLayoutY(pos_y);
            bt.setGraphic(img);



            bt.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    cardChoice = card;

                    messages.setText(card.getMsg());
                }
            });
            pos_y = pos_y+screenBounds.getMaxY()/180;




            root.getChildren().add(bt);
            if (!card.isFirstUse()){
                ImageView coin = new ImageView(new Image(getClass().getResourceAsStream("Image/coin.png")));
                coin.setFitWidth(screenBounds.getMaxY()/16);
                coin.setPreserveRatio(true);
                coin.setLayoutX((pos_max_x-screenBounds.getMaxY()/7.5)*1.01);
                coin.setLayoutY(pos_y*1.01);
                root.getChildren().add(coin);

            }
        }

    }
    public void addButtonCharacter(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        Button bt = new Button();
        bt.setText("Use Character");
        bt.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        bt.setLayoutY(screenBounds.getMaxY()*2/3 -50);
        root.getChildren().add(bt);
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                /**TODO YANFENG
                 * qui prendi la carta e la mandi al server
                 * ricordati di settare a null cardChoice una volta preso l'input
                 *
                 * carta in cardChoice
                 */
                if (cardChoice ==null)
                    messages.setText("Seleziona una carta");
                else
                    messages.setText("Invio Carta");
            }
        });
        nodes.add(bt);

    }

    // assistenti
    public void showAssistant(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(0);
        gridPane.setLayoutY(screenBounds.getMinY()/3);
        List<Assistant> cards = game.getPlayers().get(0).getHand().getList_cards();
        for(int i =0;i<10; i++) {
            String img_file;
            switch (i) {
                case 0:
                    img_file = "Image/Assistente_1.png";
                    break;
                case 1:
                    img_file = "Image/Assistente_2.png";
                    break;
                case 2:
                    img_file = "Image/Assistente_3.png";
                    break;
                case 3:
                    img_file = "Image/Assistente_4.png";
                    break;
                case 4:
                    img_file = "Image/Assistente_5.png";
                    break;
                case 5:
                    img_file = "Image/Assistente_6.png";
                    break;
                case 6:
                    img_file = "Image/Assistente_7.png";
                    break;
                case 7:
                    img_file = "Image/Assistente_8.png";
                    break;
                case 8:
                    img_file = "Image/Assistente_9.png";
                    break;
                default:
                    img_file = "Image/Assistente_10.png";
                    break;
            }
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream(img_file)));
            double dim=screenBounds.getMaxY()/7;
            Button btAssistant=new Button();
            img.setFitWidth(dim);
            img.setPreserveRatio(true);
            btAssistant.setGraphic(img);
            btAssistant.setPadding(new Insets(0.5,0.5,0.5,0.5));

            gridPane.setHgap(dim/3);
            gridPane.setVgap(dim/10);
            Assistant card = cards.get(i);
            if (cards.contains(AssistantType.index(i))){
                img.setOpacity(0.5);
            }
            else{
                btAssistant.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        assistantChoice = card;
                        messages.setText("Hai scelto una carta");
                    }
                });
            }
            if(i<5)
                gridPane.add(btAssistant,i , 0);

            else
                gridPane.add(btAssistant,i-5 , 1);
            GridPane.setHalignment(img, HPos.CENTER);
        }


        Button bt=new Button("Select Assistant");
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                /**TODO YANFENG
                 * qui prendi la carta assistente (è nella variabile globale assistantChoice)
                 */
                messages.setText("Invio Carta");
            }
        });

        gridPane.add(bt,4,3);
        nodes.add(gridPane);
        root.getChildren().add(gridPane);
    }

    //player board
    public void switcBoardController(Boolean Action){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        HBox bar = new HBox();
        ImageView img_view = new ImageView(new Image(getClass().getResourceAsStream("Image/PLANCIA_GIOCO.png")));
        img_view.setFitHeight(screenBounds.getMaxY()/3);
        img_view.setPreserveRatio(true);
        VBox vbox = new VBox();
        Button bt = new Button();
        bt.setText("Switch Board");

        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int index_player;
                Player curr_player = game.getPlayers().stream().filter(p -> p.getName().equals(board_name)).collect(Collectors.toList()).get(0);
                index_player = game.getPlayers().indexOf(curr_player);
                if (index_player+1 == game.getPlayers().size())
                    index_player = 0;
                else
                    index_player +=1;
                board_name = game.getPlayers().get(index_player).getName();
                messages.setText("you are watching "+board_name+ " board"+"to swich play the ShowOpponentBoard button");
                removeBoard();

                if (Action) {
                    showDiningRoom();
                    showTowers();
                    showProfessors();
                    showWaitingRoom(true);
                }
                else {
                    showDiningRoom();
                    showTowers();
                    showProfessors();
                    showWaitingRoom(false);
                }
            }
        });
        vbox.getChildren().add(bt);

        StackPane stackPane = new StackPane();
        VBox vbox_messages = new VBox();
        Label fixlabel = new Label();
        fixlabel.setText("MESSAGES:");
        Label label = messages;
        vbox_messages.getChildren().add(fixlabel);
        vbox_messages.getChildren().add(label);

        Pane rect = new Pane();
        rect.setPrefHeight(screenBounds.getMaxY()/3);
        rect.setPrefWidth(screenBounds.getMaxX()-img_view.getLayoutBounds().getWidth());
        rect.setStyle("-fx-background-color: #F0FFFF" );

        stackPane.getChildren().add(rect);
        stackPane.getChildren().add(vbox_messages);
        vbox.getChildren().add(stackPane);

        bar.setLayoutX(0);
        bar.setLayoutY(screenBounds.getMaxY()*2/3);
        bar.getChildren().add(img_view);
        bar.getChildren().add(vbox);
        root.getChildren().add(bar);
    }
    public void showWaitingRoom(Boolean Action){
        String red = "Image/student_red.png";
        String yellow = "Image/student_yellow.png";
        String blue = "Image/student_blue.png";
        String green = "Image/student_green.png";
        String pink = "Image/student_pink.png";
        String color;

        Player player = game.getPlayers().stream().filter(p -> p.getName().equals(board_name)).collect(Collectors.toList()).get(0);
        PlayerBoard pb = player.getPlayerBoard();
        int[] waitingRoom = pb.getWaitingRoom();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double bt_pos_x ;
        double bt_pos_y;

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


                img.setFitWidth(screenBounds.getMaxY()/(33.333));
                img.setFitHeight(screenBounds.getMaxY()/(33.333));
                img.setPreserveRatio(true);

                if (cont==0){
                    pos_x = screenBounds.getMaxY()/(14.2857);
                    pos_y = screenBounds.getHeight()/(1.4128);
                    img.setLayoutX(pos_x);
                    img.setLayoutY(pos_y);
                    cont +=1;
                }
                else if(cont<5) {
                    pos_x = screenBounds.getMaxY()/(14.2857);
                    img.setLayoutX(pos_x);
                    img.setLayoutY(pos_y+(screenBounds.getHeight()/(18))*(cont));
                    cont +=1;
                }
                else {
                    if (cont==5)
                        pos_y = screenBounds.getHeight()-225+10;

                    pos_x = screenBounds.getMaxY()/(36);
                    img.setLayoutX(pos_x);
                    img.setLayoutY(pos_y+(screenBounds.getHeight()/(18))*(cont-5));
                    cont +=1;
                }


                if (board_name.equals(name) && Action){
                    img.setOnDragDetected(new EventHandler <MouseEvent>() {
                        public void handle(MouseEvent event) {
                            /* drag was detected, start drag-and-drop gesture*/
                            System.out.println("onDragDetected");

                            /* allow any transfer mode */
                            Dragboard db = img.startDragAndDrop(TransferMode.ANY);

                            /* put a string on dragboard */
                            ClipboardContent content = new ClipboardContent();
                            content.putString("Hello!");
                            db.setContent(content);

                            event.consume();
                        }
                    });
                    img.setOnDragDone(new EventHandler <DragEvent>() {
                        public void handle(DragEvent event) {
                            /* the drag-and-drop gesture ended */
                            System.out.println("onDragDone");
                            /* if the data was successfully moved, clear it */

                            event.consume();
                        }
                    });

                }
                board.add(img);
                nodes.add(img);
                root.getChildren().add(img);
            }
        }
    }
    public void showDiningRoom(){
        String red = "Image/student_red.png";
        String yellow = "Image/student_yellow.png";
        String blue = "Image/student_blue.png";
        String green = "Image/student_green.png";
        String pink = "Image/student_pink.png";
        String color;

        Player player = game.getPlayers().stream().filter(p -> p.getName().equals(board_name)).collect(Collectors.toList()).get(0);
        PlayerBoard pb = player.getPlayerBoard();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_x = screenBounds.getMaxY()/(6.92);
        double pos_y ;
        for(int i =0; i<5;i++){
            for(int j=0; j<pb.getDiningRoom()[i];j++){
                if (i==3) {
                    color = blue;
                    pos_y = screenBounds.getMaxY()/(1.078);
                }
                else if (i==2) {
                    color = pink;
                    pos_y = screenBounds.getMaxY()/(1.147);
                }
                else if (i==1) {
                    color = yellow;
                    pos_y = screenBounds.getMaxY()/(1.225);
                }
                else if (i==0) {
                    color = red;
                    pos_y = screenBounds.getMaxY()/(1.313);
                }
                else {
                    color = green;
                    pos_y = screenBounds.getMaxY()/(1.41);
                }
                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                board.add(img);
                nodes.add(img);
                img.setFitWidth(screenBounds.getMaxY()/(33.333));
                img.setFitHeight(screenBounds.getMaxY()/(33.333));
                img.setPreserveRatio(true);
                img.setLayoutX(pos_x + j*(screenBounds.getMaxY()/(27.74)));
                img.setLayoutY(pos_y);
                root.getChildren().add(img);

            }


            Pane paneDrop = new Pane ();
            paneDrop.setPrefWidth(screenBounds.getMaxY()/2);
            paneDrop.setStyle("-fx-background-color: #F0FFFF" );
            paneDrop.setPrefHeight(screenBounds.getMaxY()-screenBounds.getMaxY()*2/3);
            paneDrop.setLayoutY(screenBounds.getMaxY()*2/3);
            paneDrop.setLayoutX(screenBounds.getMaxY()/9.5);
            paneDrop.setOpacity(0);

            paneDrop.setOnDragEntered(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    /* the drag-and-drop gesture entered the target */
                    System.out.println("onDragEntered");
                    /* show to the user that it is an actual gesture target */
                    if (event.getGestureSource() != paneDrop &&
                            event.getDragboard().hasString()) {
                        System.out.println("setOnDragEntered");
                    }

                    event.consume();
                }
            });
            paneDrop.setOnDragOver(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    //data is dragged over the target
                    System.out.println("onDragOver");

                    // accept it only if it is  not dragged from the same node
                    //( and if it has a string data
                    if (event.getGestureSource() != paneDrop &&
                            event.getDragboard().hasString()) {
                        // allow for both copying and moving, whatever user chooses
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }

                    event.consume();
                }
            });

            paneDrop.setOnDragDropped(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    /* data dropped */
                    System.out.println("onDragDropped");
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        System.out.println("setOnDragDropped");
                        /**TODO YANFENG
                         * qui puoi prendere l'isola su cui si è trascinnato uno studente e lo studente
                         */
                        success = true;
                    }
                    /* let the source know whether the string was successfully
                     * transferred and used */
                    event.setDropCompleted(success);

                    event.consume();
                }
            });
            nodes.add(paneDrop);
            root.getChildren().add(paneDrop);



        }


    }
    public void showTowers(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Player player = game.getPlayers().stream().filter(p -> p.getName().equals(board_name)).collect(Collectors.toList()).get(0);
        double pos_x= screenBounds.getMaxY()/(1.848);
        Double pos_y = screenBounds.getMaxY()/1.5;
        PlayerBoard pb = player.getPlayerBoard();
        TowerColor color ;
        GridPane gridPane = new GridPane();
        double dim = screenBounds.getMaxY() / (60);
        gridPane.setHgap(dim / 3);
        gridPane.setVgap(dim / 7.5);
        gridPane.setLayoutX(pos_x+(dim*4.66));
        gridPane.setLayoutY(pos_y+(dim*4));

        for (int i =0; i<pb.getN_tower();i++){

            color= player.getTowerColor();
            ImageView img;
            if (color.equals(TowerColor.WHITE)) {
                img = new ImageView(new Image(getClass().getResourceAsStream("Image/white_tower.png")));

            }
            else if (color.equals(TowerColor.BLACK)) {
                img = new ImageView(new Image(getClass().getResourceAsStream("Image/black_tower.png")));

            }
            else {
                img = new ImageView(new Image(getClass().getResourceAsStream("Image/grey_tower.png")));
            }
            img.setFitHeight(screenBounds.getMaxY()/(20));
            img.setPreserveRatio(true);
            if (i%2==0)
                gridPane.add(img,0,i);
            else
                gridPane.add(img,1,i-1);
            nodes.add(gridPane);
            board.add(gridPane);




        }
        root.getChildren().add(gridPane);
    }
    public void showProfessors(){
        String red = "Image/teacher_red.png";
        String yellow = "Image/teacher_yellow.png";
        String blue = "Image/teacher_blue.png";
        String green = "Image/teacher_green.png";
        String pink = "Image/teacher_pink.png";
        String color;

        Player player = game.getPlayers().stream().filter(p -> p.getName().equals(board_name)).collect(Collectors.toList()).get(0);
        Mage mage = player.getMage();
        Mage[] prof = game.getProfessors().getList_professors();

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double pos_x= screenBounds.getMaxY()/(1.848);
        double pos_y ;
        for(int i =0; i<5;i++){
            if (i==3) {
                color = blue;
                pos_y = screenBounds.getHeight()/(1.0856);
            }
            else if (i==2) {
                color = pink;
                pos_y = screenBounds.getHeight()/(1.1538);
            }
            else if (i==1) {

                color = yellow;
                pos_y = screenBounds.getHeight()/(1.2312);
            }
            else if (i==0) {
                color = red;
                pos_y = screenBounds.getHeight()/(1.3196);
            }
            else {
                color = green;
                pos_y = screenBounds.getHeight()/(1.4218);
            }
            if (prof[i]==mage) {
                ImageView img = new ImageView(new Image(getClass().getResourceAsStream(color)));
                nodes.add(img);
                board.add(img);
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

    // di supporto
    protected  void GridPane(double pos_x,double pos_y,int[] students){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double dim = screenBounds.getMaxY()/(60);

        ImageView red_student = new ImageView(new Image(getClass().getResourceAsStream(red)));
        ImageView yellow_student = new ImageView(new Image(getClass().getResourceAsStream(yellow)));
        ImageView blue_student = new ImageView(new Image(getClass().getResourceAsStream(blue)));
        ImageView green_student = new ImageView(new Image(getClass().getResourceAsStream(green)));
        ImageView pink_student = new ImageView(new Image(getClass().getResourceAsStream(pink)));
        GridPane gridPane = new GridPane();
        //island_student.setMaxSize(10,10);
        //island_student.setPreserveRatio(true);
        //island_student.setAlignment(Pos.CENTER);
        //grandezza studenti
        red_student.setFitWidth(dim);
        red_student.setFitHeight(dim);
        yellow_student.setFitWidth(dim);
        yellow_student.setFitHeight(dim);
        blue_student.setFitWidth(dim);
        blue_student.setFitHeight(dim);
        green_student.setFitWidth(dim);
        green_student.setFitHeight(dim);
        pink_student.setFitWidth(dim);
        pink_student.setFitHeight(dim);
        gridPane.setHgap(dim/3);
        gridPane.setVgap(dim/7.5);
        gridPane.setLayoutX(pos_x+(dim*4.66));
        gridPane.setLayoutY(pos_y+(dim*4));
        gridPane.add(red_student, 0, 0);
        gridPane.add(new Text(""+students[0]), 1, 0);
        gridPane.add(yellow_student, 0, 1);
        gridPane.add(new Text(""+students[1]), 1, 1);
        gridPane.add(blue_student, 0, 2);
        gridPane.add(new Text(""+students[2]), 1, 2);
        gridPane.add(green_student, 0, 3);
        gridPane.add(new Text(""+students[3]), 1, 3);
        gridPane.add(pink_student, 0, 4);
        gridPane.add(new Text(""+students[4]), 1, 4);
        //island_student.setGridLinesVisible (true);



        nodes.add(gridPane);
        root.getChildren().add(gridPane);
    }

    private void removeGame() {
        for(int i=0; i<nodes.size();i++){
            Node img = nodes.get(i);
            root.getChildren().remove(img);
        }
        nodes.clear();

    }
    private void removeBoard(){
        for(int i=0; i<board.size();i++){
            Node img = board.get(i);
            root.getChildren().remove(img);
        }
        board.clear();
    }

    protected  void TowerGridPane(double pos_x,double pos_y,int size,TowerColor color){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double dim = screenBounds.getMaxY()/(30);
        GridPane gridPane = new GridPane();
        if (color!=null) {
            ImageView tower;
            switch (color) {
                case WHITE:
                    tower = new ImageView(new Image(getClass().getResourceAsStream("Image/white_tower.png")));
                    break;
                case BLACK:
                    tower = new ImageView(new Image(getClass().getResourceAsStream("Image/black_tower.png")));
                    break;
                default:
                    tower = new ImageView(new Image(getClass().getResourceAsStream("Image/grey_tower.png")));
            }
            tower.setFitWidth(dim);
            tower.setFitHeight(1.5 * dim);
            gridPane.add(tower, 0, 0);
            GridPane.setHalignment(tower, HPos.CENTER);
        }
        else{
            ImageView tower;
            tower = new ImageView(new Image(getClass().getResourceAsStream("Image/white_tower.png")));
            tower.setFitWidth(dim);
            tower.setFitHeight(1.5 * dim);
            tower.setVisible(false);
            gridPane.add(tower, 0, 0);
        }
        gridPane.setHgap(dim/3);
        gridPane.setVgap(dim/10);
        gridPane.setLayoutX(pos_x+(dim*1.1));
        gridPane.setLayoutY(pos_y+(dim*2.5));
        gridPane.add(new Text("Size:"+size), 0, 1);
        //gridPane.setGridLinesVisible (true);



        nodes.add(gridPane);
        root.getChildren().add(gridPane);
    }

    public void noTileImg(double pos_x,double pos_y){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        ImageView noTile;
        noTile = new ImageView(new Image(getClass().getResourceAsStream("Image/deny_island_icon.png")));
        noTile.setFitWidth(screenBounds.getMaxY()/18);
        noTile.setFitHeight(screenBounds.getMaxY()/18);
        noTile.setPreserveRatio(true);
        noTile.setLayoutX(pos_x+screenBounds.getMaxY()/33);
        noTile.setLayoutY(pos_y+screenBounds.getMaxY()/33);
        nodes.add(noTile);
        root.getChildren().add(noTile);
    }

    public void showWallet(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        Player player = game.getPlayers().stream().filter(p -> p.getName().equals(board_name)).collect(Collectors.toList()).get(0);

        int wallet = player.getWallet().getSaving();
        double dim = screenBounds.getMaxY()/(15);
        double pos_x =screenBounds.getMaxX()*5/6;
        double pos_y =dim/3;
        GridPane gridPane = new GridPane();
        ImageView coin = new ImageView(new Image(getClass().getResourceAsStream("Image/coin.png")));
        coin.setFitWidth(dim);
        coin.setPreserveRatio(true);
        gridPane.add(coin, 0, 0);
        GridPane.setHalignment(coin, HPos.RIGHT);
        //gridPane.setHgap(dim/3);
        gridPane.setLayoutX(pos_x);
        gridPane.setLayoutY(pos_y);
        gridPane.add(new Text("x"+wallet), 1, 0);
        //gridPane.setGridLinesVisible (true);
        nodes.add(gridPane);
        root.getChildren().add(gridPane);
    }

    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses){

    }
}
