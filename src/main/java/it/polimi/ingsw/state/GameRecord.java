package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class GameRecord implements Serializable {
    private Game initialGameStatus;
    private Game currentGameStatus;
    private ArrayList<Command> commands;

}
