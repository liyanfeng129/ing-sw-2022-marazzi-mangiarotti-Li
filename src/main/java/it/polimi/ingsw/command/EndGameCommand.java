package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class EndGameCommand extends Command implements Serializable {
    public EndGameCommand(boolean isCliClient, Game game, String username) {
        super(isCliClient, game, username);
    }

    @Override
    public void undo(Game game) throws EriantysExceptions {

    }

    @Override
    public void getData() throws EriantysExceptions {
        setDataGathered(true);
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        return true;
    }
}
