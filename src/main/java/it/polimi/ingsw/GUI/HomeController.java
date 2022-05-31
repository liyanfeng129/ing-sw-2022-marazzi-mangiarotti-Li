package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class HomeController extends AASceneParent {
    @FXML
    private Label myLabel;
    @FXML
    private Label myMessage;
    @FXML
    private TextField myTextfield;

    private ActionEvent currentEvent;

    String name;

    @FXML
    public void submit(ActionEvent event) {
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
                new GuiMessageSender(this,Config.USER_LOGGING).run();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("start_load.fxml"));
            Parent root = loader.load();

            StartLoadController startLoad = loader.getController();
            startLoad.setInfo(getInfo()); // passing data from this stage to the next stage

            switchScene(root, currentEvent);

            System.out.println(getUsername());
        }
        else
        {
            // pop up a small window to communicate the reason why user did not log or display error
            System.out.println(responses.get(0));
            myLabel.setText((String) responses.get(0));
        }
    }

    public String getMyLabel() {
        return myLabel.getText();
    }

    public void setMyLabel(String myLabel) {
        this.myLabel.setText(myLabel);
    }
}


