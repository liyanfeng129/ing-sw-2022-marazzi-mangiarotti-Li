package it.polimi.ingsw.GUI;


import it.polimi.ingsw.characterCards2.Character12;
import it.polimi.ingsw.characterCards2.Character2;
import it.polimi.ingsw.characterCards2.Character8;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GameSetUpController extends AASceneParent{
    private ActionEvent currentEvent;
    @FXML
    protected void startEasy2(ActionEvent event) {
        System.out.println("Easy mode for 2 people");
        currentEvent = event;
        //Platform.runLater(()-> new GuiMessageSender(this, Config.CREATE_NORMAL_GAME_FOR_2).run());
        new GuiMessageSender(this, Config.CREATE_NORMAL_GAME_FOR_2).run();
        // lobby Scene where is displayed player in waiting and eventually to start game
    }
    @FXML

    protected void startEasy3(ActionEvent event) throws IOException, EriantysExceptions {
        System.out.println("Easy mode for 3 people");
        // lobby Scene where is displayed player in waiting and eventually to start game

        //bisogna passargli il game

        Game game  = new Game(3,true,new Player("leo", Mage.MAGE1,TowerColor.BLACK,3,true,false));
        game.addPlayers(new Player("ale", Mage.MAGE2,TowerColor.WHITE,3,false,false));
        game.addPlayers(new Player("yan", Mage.MAGE3,TowerColor.GREY,3,false,false));

        Player player = game.getPlayers().stream().filter(p -> p.getName()=="leo").collect(Collectors.toList()).get(0);
        player.getPlayerBoard().setDiningRoom(new int[]{10, 10, 10, 1, 10});
        player.getPlayerBoard().addStudentsToWaitingRoom(new int[]{1, 1, 3, 1, 3}); // e se sono troppi ?

        player = game.getPlayers().stream().filter(p -> p.getName()=="ale").collect(Collectors.toList()).get(0);
        player.getPlayerBoard().setDiningRoom(new int[]{3, 5, 2, 10, 4});
        player.getPlayerBoard().addStudentsToWaitingRoom(new int[]{0, 1, 5, 1, 2}); // e se sono troppi ?

        player = game.getPlayers().stream().filter(p -> p.getName()=="yan").collect(Collectors.toList()).get(0);
        player.getPlayerBoard().setDiningRoom(new int[]{1, 1, 2, 2, 9});
        player.getPlayerBoard().addStudentsToWaitingRoom(new int[]{2, 0, 0, 5, 2}); // e se sono troppi ?


        game.getProfessors().assignProfessor(game.getPlayers());
        game.getTable().addCharacterCards(new Character12());
        game.getTable().addCharacterCards(new Character2());
        game.getTable().addCharacterCards(new Character8());
        game.getTable().getIsland(5).setTower(TowerColor.BLACK);
        game.getTable().getIsland(3).setTower(TowerColor.WHITE);
        game.getTable().getIsland(7).setTower(TowerColor.GREY);
        game.getTable().getIsland(7).setNoEntryTiles(true);
        game.getTable().getIsland(4).setNoEntryTiles(true);
        game.getTable().getIsland(0).setNoEntryTiles(true);
        for(int i = 0; i < 3; i++){
            Cloud cloud = new Cloud();
            cloud.setCloud(3);
            game.getTable().getClouds().add(cloud);
        }


        //



        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();

        GameBoardController controller = loader.getController();
        controller.setGame(game);

        switchScene(root, event);


    }

    @FXML
    protected void startExp2(ActionEvent event) throws IOException, EriantysExceptions {
        Game game  = new Game(3,true,new Player("leo", Mage.MAGE1,TowerColor.BLACK,3,true,false));
        game.addPlayers(new Player("ale", Mage.MAGE2,TowerColor.WHITE,3,false,false));
        game.addPlayers(new Player("yan", Mage.MAGE3,TowerColor.GREY,3,false,false));
        System.out.println("Expert mode for 2 people");
        // lobby Scene where is displayed player in waiting and eventually to start game

        FXMLLoader loader = new FXMLLoader(getClass(). getResource("PlayerWaitingRoom.fxml"));
        Parent root = loader.load();
        PlayerWaitingRoomController playerWaitingRoom = loader.getController();
        getInfo().setGame(game);
        playerWaitingRoom.setInfo(getInfo());
        switchScene(root,event);

        /*
         FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();
        GameBoardController controller = loader.getController();
        controller.setGame(game);
        switchScene(root, event);
         */
    }
    @FXML
    protected void startExp3(ActionEvent event) {
        System.out.println("Expert mode for 3 people");
        // lobby Scene where is displayed player in waiting and eventually to start game
    }
    @FXML
    protected void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass(). getResource("start_load.fxml"));
        Parent root = loader.load();
        StartLoadController startLoad = loader.getController();
        startLoad.setInfo(getInfo());
        switchScene(root,event);
        System.out.println("back");
    }

    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {
        System.out.println("sono in response from sender di gamesetup");
        if(responses.get(0).equals(Config.CREATE_NORMAL_GAME_FOR_2_SUC))
        {
            Game game = (Game) responses.get(1);
            System.out.println(game.toString());
            FXMLLoader loader = new FXMLLoader(getClass(). getResource("PlayerWaitingRoom.fxml"));
            Parent root = loader.load();
            PlayerWaitingRoomController playerWaitingRoom = loader.getController();
            getInfo().setGame(game);
            playerWaitingRoom.setInfo(getInfo());
            switchScene(root,currentEvent);
        }
    }

}