package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Game;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import it.polimi.ingsw.model.*;


import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

public class LoadGameController extends AASceneParent {
    @FXML
    private ListView<String> games;
    private String CurrGame;
    private ArrayList<String> roomIdentifier = new ArrayList<>();
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
        if(getInfo().getLoadGameOption() == GUIInfo.RESUME_OLD_GAME)
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
        getInfo().setGameCreatorName(roomIdentifier.get(games.getSelectionModel().getSelectedIndex()));
        if(getInfo().getLoadGameOption() == GUIInfo.LOAD_GAME)
            Platform.runLater(()->new GuiMessageSender(this, Config.JOIN_ONE_GAME).run());
        if(getInfo().getLoadGameOption() == GUIInfo.RESUME_OLD_GAME)
        {
            getInfo().setGameStartedDate(roomIdentifier.get(games.getSelectionModel().getSelectedIndex()));
            Platform.runLater(()->new GuiMessageSender(this, Config.RELOAD_AN_OLD_GAME).run());
        }
        if(getInfo().getLoadGameOption() == GUIInfo.LOAD_OlD_GAME)
            Platform.runLater(()->new GuiMessageSender(this, Config.JOIN_RESUMABLE_GAME).run());

        System.out.println("load_game_button_clicked");
    }



    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {
        System.out.println("LoadGame: sender call back");
        String option = (String) responses.get(0);
        ArrayList<Game> gameList;
        Game game;
        int i = 0;
        switch (option)
        {
            case Config.SHOW_RESUMABLE_GAMES_SUC:
                gameList = (ArrayList<Game>) responses.get(1);
                i = 0;
                for(Game g : gameList)
                {
                    roomIdentifier.add(g.getPlayers().get(0).getName());
                    games.getItems().add(String.format("%d. %s's %s game for %d, started in %s with player: %s, %s .",
                            i+1,
                            g.getTurnList().get(0).getName(),
                            (g.isExpertMode()? "expert" : "normal"),
                            g.getN_Player(),
                            g.getGameStartingTime(),
                            (g.getN_Player() > 1 ? g.getTurnList().get(1).getName() : " " ),
                            (g.getN_Player() > 2 ? g.getTurnList().get(2).getName() : " " )
                    ));
                    i++;
                }
                break;
            case Config.RESUME_OLD_GAMES_SUC:
                gameList = (ArrayList<Game>) responses.get(1);
                i = 0;
                for(Game g : gameList)
                {
                    roomIdentifier.add(g.getGameStartingTime());
                    games.getItems().add(String.format("%d. %s's %s game for %d, started in %s with player: %s, %s .",
                            i+1,
                            g.getTurnList().get(0).getName(),
                            (g.isExpertMode()? "expert" : "normal"),
                            g.getN_Player(),
                            g.getGameStartingTime(),
                            (g.getN_Player() > 1 ? g.getTurnList().get(1).getName() : " " ),
                            (g.getN_Player() > 2 ? g.getTurnList().get(2).getName() : " " )
                    ));
                    i++;
                }
                break;
            case Config.SHOW_EXISTING_GAMES_SUC:
                gameList = (ArrayList<Game>) responses.get(1);
                i = 0;
                for(Game g : gameList)
                {
                    roomIdentifier.add(g.getPlayers().get(0).getName());
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
            case Config.RELOAD_AN_OLD_GAME_SUC:
            case Config.JOIN_RESUMABLE_GAME_SUC:
                game = (Game) responses.get(1);
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
