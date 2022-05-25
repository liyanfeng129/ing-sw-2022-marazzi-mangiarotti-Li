package it.polimi.ingsw.GUI;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartLoadController extends AASceneParent {




    @FXML
    protected void newGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass(). getResource("game_set_up.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("newGame");
        System.out.println(getUsername());
    }
    @FXML
    protected void loadGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass(). getResource("load_game.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("loadGame");
    }
    @FXML
    protected void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass(). getResource("home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("back");
    }
}