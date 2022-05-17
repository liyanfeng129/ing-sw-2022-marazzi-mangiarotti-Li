package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class UseCharacterCommand extends Command implements Serializable {

    public UseCharacterCommand(boolean isCliClient, Game game, String username) {
        super(isCliClient, game, username);
    }

    @Override
    public void undo() throws EriantysExceptions {

    }

    @Override
    public void getData() throws EriantysExceptions {

    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        return false;
    }
}
