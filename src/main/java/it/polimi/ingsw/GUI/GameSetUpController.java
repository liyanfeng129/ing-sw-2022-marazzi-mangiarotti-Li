package it.polimi.ingsw.GUI;


import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

public class GameSetUpController extends AASceneParent{
    private ActionEvent currentEvent;
    @FXML
    protected void startEasy2(ActionEvent event) {
        System.out.println("Easy mode for 2 people");
        currentEvent = event;
        Platform.runLater(()-> new GuiMessageSender(this, Config.CREATE_NORMAL_GAME_FOR_2).run());
        //new GuiMessageSender(this, Config.CREATE_NORMAL_GAME_FOR_2).run();
        // lobby Scene where is displayed player in waiting and eventually to start game
    }
    @FXML

    protected void startEasy3(ActionEvent event) throws IOException, EriantysExceptions {
        System.out.println("Easy mode for 3 people");
        currentEvent = event;
        Platform.runLater(()-> new GuiMessageSender(this, Config.CREATE_NORMAL_GAME_FOR_3).run());

    }

    @FXML
    protected void startExp2(ActionEvent event) throws IOException, EriantysExceptions {
        System.out.println("Expert mode for 2 people");
        currentEvent = event;
        Platform.runLater(()-> new GuiMessageSender(this, Config.CREATE_EXPERT_GAME_FOR_2).run());

    }
    @FXML
    protected void startExp3(ActionEvent event) {
        System.out.println("Expert mode for 3 people");
        currentEvent = event;
        Platform.runLater(()-> new GuiMessageSender(this, Config.CREATE_EXPERT_GAME_FOR_3).run());
    }
    @FXML
    protected void back(ActionEvent event) throws IOException {
        switchScene((Stage) ((Node)event.getSource()).getScene().getWindow(), FxmlNames.START_LOAD);
        System.out.println("back");
    }
    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {
        System.out.println("sono in response from sender di gamesetup");
        String msg = (String) responses.get(0);
        switch(msg)
        {
            case Config.CREATE_NORMAL_GAME_FOR_2_SUC:
            case Config.CREATE_EXPERT_GAME_FOR_2_SUC:
                Game game = (Game) responses.get(1);
                getInfo().setGame(game);
                switchScene((Stage) ((Node)currentEvent.getSource()).getScene().getWindow(),FxmlNames.PLAYER_WAITING_Room);
                break;
        }
    }

    @Override
    public void errorCommunicate(Exception e) {

    }

}