package it.polimi.ingsw.GUI;


import it.polimi.ingsw.model.Config;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

public class StartLoadController extends AASceneParent {

    ActionEvent currentEvent;
    @FXML
    private AnchorPane root;

    @FXML
    private void initialize(){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                initConfig();
            }
        });
    }

    protected void initConfig()
    {
        getInfo().getListener().setCaller(this);
        Task task = new Task<Void>() {
            @Override public Void call() {
                getInfo().getListener().start(); // running listener
                System.out.println("Running: "+ getInfo().getListener().toString());
                return null;
            }
        };
        System.out.println(getInfo().getListener().toString());
        new Thread(task).start();
    }

    @FXML
    protected void newGame(ActionEvent event) throws IOException {
        getInfo().setLoadGameOption(GUIInfo.LOAD_GAME);
        switchScene((Stage) ((Node)event.getSource()).getScene().getWindow(),FxmlNames.GAME_SET_UP);
        System.out.println("new game");
    }
    @FXML
    protected void loadGame(ActionEvent event) throws IOException {
        getInfo().setLoadGameOption(GUIInfo.LOAD_GAME);
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
    protected void startOldGame() throws IOException {
        getInfo().setLoadGameOption(GUIInfo.RESUME_OLD_GAME);
        switchScene((Stage) root.getScene().getWindow(), FxmlNames.LOAD_GAME);
        System.out.println("start old game");
    }

    @FXML
    protected void loadOldGame() throws IOException {
        getInfo().setLoadGameOption(GUIInfo.LOAD_OlD_GAME);
        switchScene((Stage) root.getScene().getWindow(), FxmlNames.LOAD_GAME);
        System.out.println("load old game");
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
        if(e instanceof SocketException)
        {
            System.out.println("ERROR communicate: Something went wrong with server");
            getInfo().setMessage("Something went wrong with server");

                Platform.runLater(()->{

                    try {
                        switchScene((Stage) root.getScene().getWindow(),FxmlNames.END_GAME);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        }
        else
            e.printStackTrace();
    }
}