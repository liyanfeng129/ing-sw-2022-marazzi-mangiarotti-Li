package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import javafx.application.Platform;
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
import java.util.ResourceBundle;

public class PlayerWaitingRoomController extends AASceneParent  {
    @FXML
    private AnchorPane root;

    @FXML
    private void initialize(){

        Platform.runLater(new Runnable() {

            @Override public void run() {
                //getInfo().getGame().getPlayers().get(0).getName().equals(getUsername())
                if (true) {
                    setPlayerLabel();
                    addButton();
                }


            }
        });

    }

   @FXML
   private Label PlayerLabel;

   public void setPlayerLabel(){
       /**TODO YANFENG
        *
        * qui puoi settare i giocatori  in PlayersNames
        *
        * se ne metti uno alla volta puoi fare
        * PlayerLabel.getText() + "\n" + new player stirng
        */
       /*
       Game game = getInfo().getGame();
       String message = (game.getPlayers().get(0).equals(getInfo().getUserName())? "This is creator's game room, only he can start the game.\n" : "" )
               +String.format("Game mode: %s\n" +
                       "needs %d participant\n" +
                       "waiting for %d",
               (game.isExpertMode()? "expert" : "normal"),
               game.getN_Player(),
               game.getN_Player()-game.getPlayers().size()
       );
        */
       PlayerLabel.setText(" a");

   }

    public void addButton(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        Button bt = new Button();
        bt.setText("StartNewGame");
        bt.setLayoutX(screenBounds.getMaxX()-screenBounds.getMaxY()/7.5);
        bt.setLayoutY(screenBounds.getMaxY()*2/3 -50);
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                /**TODO YANFENG
                 * Qui gli fai fare quello che vuoi
                 */
            }
        });
        root.getChildren().add(bt);

    }









    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {

    }
    /**
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(true)
            //showButton();
        setPlayerLabel();
    }
    */
}
