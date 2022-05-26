module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;


    exports it.polimi.ingsw.GUI;
    opens it.polimi.ingsw.GUI to javafx.fxml;

}