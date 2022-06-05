package it.polimi.ingsw.GUI;


import it.polimi.ingsw.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSetUpController extends AASceneParent {





    @FXML
    protected void startEasy2(ActionEvent event) {
        System.out.println("Easy mode for 2 people");
    }
    @FXML
    protected void startEasy3(ActionEvent event) throws IOException, EriantysExceptions {

        Game game  = new Game(3,false,new Player("leo", Mage.MAGE1,TowerColor.BLACK,3,true,false));
        for(int i = 0; i < 3; i++){
            Cloud cloud = new Cloud();
            cloud.setCloud(3);
            game.getTable().getClouds().add(cloud);
        }

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