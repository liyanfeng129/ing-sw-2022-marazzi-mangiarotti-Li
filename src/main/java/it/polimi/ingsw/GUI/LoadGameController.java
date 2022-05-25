package it.polimi.ingsw.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadGameController extends AASceneParent implements Initializable {
    @FXML
    private ListView<String> games;

    String[] games_test = {"game_1","game_2","game_3","game_4","game_5","game_6"};
    String CurrGame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        games.getItems().addAll(games_test);
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
        Parent root = FXMLLoader.load(getClass(). getResource("start_load.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("back");
    }
    @FXML
    protected void loadGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass(). getResource("game_started.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("game_start");
    }




}
