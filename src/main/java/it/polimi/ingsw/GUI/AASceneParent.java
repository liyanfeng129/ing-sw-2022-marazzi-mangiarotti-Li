package it.polimi.ingsw.GUI;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public abstract class AASceneParent {
    private String username;
    private GUIInfo info;
    public Scene scene;
    public Stage stage;


    public String getUsername() {
        return username;
    }

    public GUIInfo getInfo() {
        return info;
    }

    public void setInfo(GUIInfo info) {
        this.info = info;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void switchScene(Parent root, Event event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public abstract void listenerCallBack(ArrayList<Object> responses);

    public abstract void responsesFromSender(ArrayList<Object> responses) throws IOException;

}
