package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class HomeController extends AASceneParent {
    @FXML
    private TextField ServerId;

    @FXML
    private Label myLabel;
    @FXML
    private Label myMessage;
    @FXML
    private TextField myTextfield;

    private ActionEvent currentEvent;

    private String name;

    @FXML
    public void submit(ActionEvent event) {
        /**TODO YAN
         * qui devi aggiungere nelle condizioni il caso in cui il server non è valido
         */
        String serverAddress = ServerId.getText();

        try {
            currentEvent = event;
            name = myTextfield.getText();
            if (name.length() == 0) {
                myMessage.setText("PLS INSERT A VALID NAME");
            } else {
                myLabel.setText("YOU HAVE ENTERED " + name);

                // richiesta server
                // recezione input
                setInfo(new GUIInfo()); // first creation of GUIInfo
                getInfo().setUserName(name);
                getInfo().setServerAddress(serverAddress);
                //new GuiMessageSender(this,Config.USER_LOGGING).run();
                Platform.runLater(()-> new GuiMessageSender(this,Config.TRY_TO_CONNECT).run());

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void listenerCallBack(ArrayList<Object> responses) {

    }

    @Override
    public void responsesFromSender(ArrayList<Object> responses) throws IOException {
        if(responses.get(0).equals(Config.USER_LOGGED) || responses.get(0).equals(Config.USER_CREATED_AND_LOGGED))
        {
            System.out.println(responses.get(0));
            //initialize guiListener
            getInfo().setListener(new GuiListener(getInfo().getListeningPortNumber(),getInfo().getUserName(),getInfo().getServerAddress()));
            switchScene((Stage) myLabel.getScene().getWindow(), FxmlNames.START_LOAD);
            //switchScene((Stage) ((Node)currentEvent.getSource()).getScene().getWindow(), FxmlNames.START_LOAD);
            System.out.println(getUsername());
        }
        else if(responses.get(0).equals(Config.TRY_TO_CONNECT_SUC))
        {
            Platform.runLater(()-> new GuiMessageSender(this,Config.USER_LOGGING).run());
        }
        else
        {
            // pop up a small window to communicate the reason why user did not log or display error
            System.out.println(responses.get(0));
            myLabel.setText((String) responses.get(0));
        }
    }

    @Override
    public void errorCommunicate(Exception e) {
        if(e instanceof UnknownHostException)
            myLabel.setText("Wrong server address, please make sure it is valid.");
        else if(e instanceof ConnectException)
            myLabel.setText("Cannot connect to server.");
        else
        {
            e.printStackTrace();
            myLabel.setText("Error 933");
        }
    }

    public String getMyLabel() {
        return myLabel.getText();
    }

    public void setMyLabel(String myLabel) {
        this.myLabel.setText(myLabel);
    }
}


