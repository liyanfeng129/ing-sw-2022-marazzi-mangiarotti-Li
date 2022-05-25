module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;



    exports it.polimi.ingsw.GUI;
    opens it.polimi.ingsw.GUI to javafx.fxml;

}