package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class PlayerWaitingRoomController extends AASceneParent  {
    @FXML
    private AnchorPane root;
    private int i = 0;

    @FXML
    private void initialize(){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                initConfig();
                setPlayerLabel();
                if (getInfo().getGame().getPlayers().get(0).getName().equals(getInfo().getUserName()))
                    addButton();
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
   private Label PlayerLabel;

   public void setPlayerLabel(){


       Game game = getInfo().getGame();
       String message = (game.getPlayers().get(0).getName().equals(getInfo().getUserName())? "This is creator's game room, only he can start the game.\n" : "" )
               +String.format("Game mode: %s\n" +
                       "needs %d participant\n" +
                       "waiting for %d",
               (game.isExpertMode()? "expert" : "normal"),
               game.getN_Player(),
               game.getN_Player()-game.getPlayers().size()
       );
       message = message + "Participants:\n";

       for (int i = 0 ; i < game.getPlayers().size(); i++)
           message = message + String.format("%d: %s\n",
                   i + 1,
                   game.getPlayers().get(i).getName()
           );
        System.out.println(message);
       PlayerLabel.setText(message);

   }

    public void addButton(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        Button bt = new Button();
        bt.setText("StartNewGame");
        bt.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        bt.setLayoutY(screenBounds.getMaxY()*2/3 -50);
        AASceneParent caller = this;
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("PlayerWaitingRoom: button clicked");
                if(getInfo().getGame().getN_Player() == getInfo().getGame().getPlayers().size())
                {
                    System.out.println("condition true");
                    Platform.runLater( ()-> new GuiMessageSender(caller, Config.GAME_START).run());
                }
            }
        });
        root.getChildren().add(bt);

    }

    @Override
    public void listenerCallBack(ArrayList<Object> responses) throws IOException {
        String msg = (String) responses.get(0);
        Game game;
        String labelMessage = "";
        switch (msg)
        {
            case Config.UPDATE_CREATOR_WAITING_ROOM :
                System.out.println("PlayerWaitingRoom: Update other waiting room");
                game = (Game) responses.get(1);
                labelMessage = String.format("%s, this is your game, waiting for other %d\n",
                        game.getPlayers().get(0).getName(),
                        game.getN_Player() - game.getPlayers().size()
                );
                labelMessage = labelMessage + "Participants:\n";

                for (int i = 0 ; i < game.getPlayers().size(); i++)
                    labelMessage = labelMessage + String.format("%d: %s\n",
                            i + 1,
                            game.getPlayers().get(i).getName()
                    );
                String finalLabelMessage = labelMessage;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        PlayerLabel.setText(finalLabelMessage);
                        getInfo().setGame(game);
                    }
                });

                break;
            case Config.UPDATE_OTHER_WAITING_ROOM:
                System.out.println("PlayerWaitingRoom: Update other waiting room");
                game = (Game) responses.get(1);
                labelMessage = labelMessage + "Please wait for the game to be started\n";
                labelMessage = labelMessage + "Participants:\n";

                for (int i = 0; i < game.getPlayers().size(); i++)
                    labelMessage = labelMessage + String.format("%d: %s\n",
                            i + 1,
                            game.getPlayers().get(i).getName()
                    );
                 finalLabelMessage = labelMessage;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        PlayerLabel.setText(finalLabelMessage);
                        getInfo().setGame(game);
                    }
                });
                break;
            case Config.GAME_UPDATED:
                System.out.println("PlayerWaitingRoom: Game_Update, go to game Board");
                game = (Game) responses.get(1);
                getInfo().getListener().setReceiverOn(false);
                getInfo().setGame(game);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            switchScene((Stage) PlayerLabel.getScene().getWindow(),FxmlNames.GAME_BOARD);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {
        if(responses.get(0).equals(Config.GAME_START_SUC))
        {
            System.out.println("PlayerWaitingRoom: SenderResponse: game started, waiting for update");
        }
    }
}
