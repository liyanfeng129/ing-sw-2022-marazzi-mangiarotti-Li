package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.EriantysExceptions;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayerWaitingRoomController extends AASceneParent implements Initializable {


    @FXML
    private void initialize(){

        Platform.runLater(new Runnable() {

            @Override public void run() {
                    if (getInfo().getGame().getPlayers().get(0).getName().equals(getUsername()))
                         showButton();

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
       String PlayersNames = "";
       PlayerLabel.setText(PlayersNames);

   }


   public void showButton(){

       Rectangle2D screenBounds = Screen.getPrimary().getBounds();
       Button bt = new Button();
       bt.setText("Start Game");
       bt.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent e) {
               /**TODO YANFENG
                * Qui gli fai fare quello che vuoi
                */
           }
       });
       bt.setLayoutX(screenBounds.getMaxX()*2/3);
       bt.setLayoutY(screenBounds.getMaxY()*2/3);
   }









    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
