package it.polimi.ingsw.GUI;


import it.polimi.ingsw.characterCards2.Character12;
import it.polimi.ingsw.characterCards2.Character2;
import it.polimi.ingsw.characterCards2.Character8;
import it.polimi.ingsw.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;

public class GameSetUpController extends AASceneParent {





    @FXML
    protected void startEasy2(ActionEvent event) {
        System.out.println("Easy mode for 2 people");
    }
    @FXML
    protected void startEasy3(ActionEvent event) throws IOException, EriantysExceptions {


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
    protected void startExp2(ActionEvent event) {
        System.out.println("Expert mode for 2 people");
    }
    @FXML
    protected void startExp3(ActionEvent event) {
        System.out.println("Expert mode for 3 people");
    }
    @FXML
    protected void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass(). getResource("start_load.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("back");
    }

}