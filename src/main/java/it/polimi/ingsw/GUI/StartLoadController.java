package it.polimi.ingsw.GUI;


import it.polimi.ingsw.model.Config;
import javafx.application.Platform;
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

    ActionEvent currentEvent;


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
        currentEvent = event;
        Platform.runLater(()->new GuiMessageSender(this, Config.LOG_OUT).run());
        System.out.println("back");
    }

    @FXML
    protected void startOldGame(){

    }

    @FXML
    protected void loadOldGame(){

    }

    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {
        if(responses.get(0).equals(Config.LOG_OUT_SUC))
        {
            setInfo(new GUIInfo());
            switchScene((Stage) ((Node)currentEvent.getSource()).getScene().getWindow(), FxmlNames.HOME);
        }
    }

    @Override
    public void errorCommunicate(Exception e) {

    }
}