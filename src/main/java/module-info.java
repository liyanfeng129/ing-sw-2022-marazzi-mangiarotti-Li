module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;


    exports it.polimi.ingsw.GUI;
    opens it.polimi.ingsw.GUI to javafx.fxml;
    exports it.polimi.ingsw.client;
    opens it.polimi.ingsw.client to com.google.gson ;
    exports it.polimi.ingsw.threads;
    opens it.polimi.ingsw.threads to com.google.gson;
}