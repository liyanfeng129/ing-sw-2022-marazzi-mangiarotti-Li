package it.polimi.ingsw.GUI;

import javafx.fxml.FXMLLoader;
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

    /**
     * @param fxmlName is the graphic that is about to be displayed
     * @param stage is current stage which is about to be changed
     *              this method switches pages
     * */
    public void switchScene(Stage stage, String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
        Parent root = loader.load();
        AASceneParent aaSceneParent = loader.getController();
        aaSceneParent.setInfo(getInfo());
        this.stage = stage;
        scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.setFullScreen(true);
        this.stage.show();
    }

    public abstract void listenerCallBack(ArrayList<Object> responses) throws IOException;

    public abstract void responsesFromSender(ArrayList<Object> responses) throws IOException;

    public abstract void errorCommunicate(Exception e);

}
