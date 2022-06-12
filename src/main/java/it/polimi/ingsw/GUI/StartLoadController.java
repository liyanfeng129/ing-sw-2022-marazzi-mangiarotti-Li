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
        switchScene((Stage) ((Node)event.getSource()).getScene().getWindow(),FxmlNames.GAME_SET_UP);
        System.out.println("new game");
    }
    @FXML
    protected void loadGame(ActionEvent event) throws IOException {
        switchScene((Stage) ((Node)event.getSource()).getScene().getWindow(), FxmlNames.LOAD_GAME);
        System.out.println("loadGame");
    }
    @FXML
    protected void back(ActionEvent event) throws IOException {
        switchScene((Stage) ((Node)event.getSource()).getScene().getWindow(), FxmlNames.HOME);
        System.out.println("back");
    }

    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) {

    }
}