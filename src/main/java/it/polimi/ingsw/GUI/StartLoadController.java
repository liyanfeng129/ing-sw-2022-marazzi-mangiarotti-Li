package it.polimi.ingsw.GUI;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class StartLoadController extends AASceneParent {



    @FXML
    protected void newGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game_set_up.fxml"));
        Parent root = loader.load();
        GameSetUpController gameSetUp = loader.getController();
        gameSetUp.setInfo(getInfo());
        switchScene(root,event);
        System.out.println("new game");
    }
    @FXML
    protected void loadGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("load_game.fxml"));
        Parent root = loader.load();
        LoadGameController loadGame = loader.getController();
        loadGame.setInfo(getInfo());
        switchScene(root,event);
        System.out.println("loadGame");
    }
    @FXML
    protected void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        HomeController home = loader.getController();
        home.setInfo(getInfo());
        switchScene(root,event);
        System.out.println("back");
    }

    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) {

    }
}