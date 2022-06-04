package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameBoardController extends AASceneParent {

    @FXML
    private Label messages;

    protected void changeScene(int scene ) {
        // cambia scena in base all'int
    }

    protected void serverMessages(String event) {
        this.messages.setText(event);
    }






    @FXML
    protected void ShowCaracter(ActionEvent event) {
        this.messages.setText("ShowCaracter");
    }
    @FXML
    protected void ShowOpponentBoard(ActionEvent event) {
        this.messages.setText("ShowOpponentBoard");
    }

    @FXML
    protected void ShowAssistant(ActionEvent event) throws IOException {
        this.messages.setText("ShowAssistant");
    }



}