package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Config;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EndGameController extends AASceneParent{

    @FXML
    Label label;
    @FXML
    AnchorPane root;
    @FXML

    private void initialize(){
        Platform.runLater(() -> {
            try {
                initConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void initConfig() {
        label.setText(getInfo().getMessage());
    }

    @FXML
    public void back(){
        Platform.runLater(()->{
            try {
                if(label.getText().equals(Config.GAME_OVER_CAUSE_ONE_IS_OFFLINE) || label.getText().equals(Config.GAME_OVER_CAUSE_AFK))
                    switchScene((Stage) root.getScene().getWindow(),FxmlNames.HOME);
                else {
                    setInfo(new GUIInfo());
                    switchScene((Stage) root.getScene().getWindow(),FxmlNames.HOME);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setLabel(){

    }


    @Override
    public void listenerCallBack(ArrayList<Object> responses) throws IOException {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {

    }

    @Override
    public void errorCommunicate(Exception e) {

    }
}
