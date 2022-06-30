package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Game;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import it.polimi.ingsw.model.*;


import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadGameController extends AASceneParent {
    @FXML
    private ListView<String> games;
    private String CurrGame;
    private ArrayList<String> roomName;
    private ActionEvent currentEvent;
    @FXML
    private AnchorPane root;

    @FXML
    private void initialize(){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                try {
                    initConfig();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void initConfig() {
        Platform.runLater(()->getInfo().getListener().setCaller(this));
        if(getInfo().getLoadGameOption() == GUIInfo.LOAD_GAME)
            Platform.runLater(()->new GuiMessageSender(this, Config.SHOW_EXISTING_GAMES).run());
        if(getInfo().getLoadGameOption() == GUIInfo.START_OLD_GAME)
            Platform.runLater(()->new GuiMessageSender(this, Config.RESUME_OLD_GAMES).run());
        if(getInfo().getLoadGameOption() == GUIInfo.LOAD_OlD_GAME)
            Platform.runLater(()->new GuiMessageSender(this, Config.SHOW_RESUMABLE_GAMES).run());



        games.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                CurrGame = games.getSelectionModel().getSelectedItem();
                System.out.println(CurrGame);
            }
        });
    }



    @FXML
    protected void back(ActionEvent event) throws IOException {
        switchScene((Stage) ((Node)event.getSource()).getScene().getWindow(),FxmlNames.START_LOAD);
    }


    /**
     * Saving room name in gameInfo(), so GuiMessageSender can extract from this controller
     * */
    @FXML
    protected void loadGame(ActionEvent event) throws IOException {
        currentEvent = event;
        getInfo().setGameCreatorName(roomName.get(games.getSelectionModel().getSelectedIndex()));
        if(getInfo().getLoadGameOption() == GUIInfo.LOAD_GAME)
            Platform.runLater(()->new GuiMessageSender(this, Config.JOIN_ONE_GAME).run());
        if(getInfo().getLoadGameOption() == GUIInfo.START_OLD_GAME)
            Platform.runLater(()->new GuiMessageSender(this, Config.GAME_OLD_START).run());
        if(getInfo().getLoadGameOption() == GUIInfo.LOAD_OlD_GAME)
            Platform.runLater(()->new GuiMessageSender(this, Config.RELOAD_AN_OLD_GAME).run());

        System.out.println("load_game_button_clicked");
    }



    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {
        System.out.println("LoadGame: sender call back");
        String option = (String) responses.get(0);
        switch (option)
        {
            case Config.SHOW_RESUMABLE_GAMES_SUC:

                break;
            case Config.RESUME_OLD_GAMES_SUC:

                break;
            case Config.SHOW_EXISTING_GAMES_SUC:
                ArrayList<Game> gameList = (ArrayList<Game>) responses.get(1);
                int i = 0;
                roomName = new ArrayList<>();
                for(Game g : gameList)
                {
                    roomName.add(g.getPlayers().get(0).getName());
                    games.getItems().add(String.format("%d. %s's %s game for %d, waiting for other %d.",
                            i+1,
                            g.getPlayers().get(0).getName(),
                            (g.isExpertMode()? "expert" : "normal"),
                            g.getN_Player(),
                            g.getN_Player()-g.getPlayers().size()
                    ));
                    i++;
                }
                break;
            case Config.JOIN_ONE_GAME_SUC:
                Game game = (Game) responses.get(1);
                getInfo().setGame(game);
                switchScene((Stage) ((Node)currentEvent.getSource()).getScene().getWindow(),FxmlNames.PLAYER_WAITING_Room);
                System.out.println("LoadGame to PlayerWaitingRoom");
                break;
            default:
                System.out.println(responses.get(0));
                // open a window to display message
                break;
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
