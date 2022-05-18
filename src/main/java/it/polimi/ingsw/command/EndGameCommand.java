package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class EndGameCommand extends Command implements Serializable {
    private ArrayList<String> winners;
    public EndGameCommand(boolean isCliClient, Game game, String username, ArrayList<String> winners) {
        super(isCliClient, game, username);
        this.winners = winners;
    }

    @Override
    public void undo(Game game) throws EriantysExceptions {

    }

    @Override
    public void getData() throws EriantysExceptions {
        if (winners.size()>1)
            System.out.println("the winners are" +winners);
        else
            System.out.println("the winner is" +winners);
        setDataGathered(true);
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        return false;
    }
}
