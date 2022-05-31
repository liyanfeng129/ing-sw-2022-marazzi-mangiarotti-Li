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

public class GameSetUpController extends AASceneParent {





    @FXML
    protected void startEasy2(ActionEvent event) {
        System.out.println("Easy mode for 2 people");
        // lobby Scene where is displayed player in waiting and eventually to start game
    }
    @FXML
    protected void startEasy3(ActionEvent event) {
        System.out.println("Easy mode for 3 people");
        // lobby Scene where is displayed player in waiting and eventually to start game
    }
    @FXML
    protected void startExp2(ActionEvent event) {
        System.out.println("Expert mode for 2 people");
        // lobby Scene where is displayed player in waiting and eventually to start game
    }
    @FXML
    protected void startExp3(ActionEvent event) {
        System.out.println("Expert mode for 3 people");
        // lobby Scene where is displayed player in waiting and eventually to start game
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

    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) {

    }
}