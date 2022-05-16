package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class EndGameCommand extends Command implements Serializable {
    private String winner;
    public EndGameCommand(boolean isCliClient, Game game, String username,String winner) {
        super(isCliClient, game, username);
        this.winner = winner;
    }

    @Override
    public void undo() throws EriantysExceptions {

    }

    @Override
    public void getData() throws EriantysExceptions {
        System.out.println("the winner is" +winner);
        setDataGathered(true);
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        return false;
    }
}
