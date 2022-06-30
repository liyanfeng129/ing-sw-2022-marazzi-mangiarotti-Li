package it.polimi.ingsw.GUI;

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

        setInfo(new GUIInfo());
        Platform.runLater(()->{
            try {
                switchScene((Stage) root.getScene().getWindow(),FxmlNames.HOME);
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
