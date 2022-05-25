package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomeController extends AASceneParent {
    @FXML
    private Label myLabel;
    @FXML
    private Label myMessage;
    @FXML
    private TextField myTextfield;


    String name;

    @FXML
    protected void submit(ActionEvent event) {
        try {
            name = myTextfield.getText();
            if (name.length() == 0) {
                myMessage.setText("PLS INSERT A VALID NAME");
            } else {
                myLabel.setText("YOU HAVE ENTERED " + name);

                // richiesta server
                // recezione input


                setUsername(name);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("start_load.fxml"));
                Parent root = loader.load();

                StartLoadController startLoad = loader.getController();
                startLoad.setUsername(name);

                switchScene(root, event);

                System.out.println(getUsername());
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}


