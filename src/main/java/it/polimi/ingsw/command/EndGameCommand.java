package it.polimi.ingsw.command;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EndGameCommand extends Command implements Serializable {
    private ArrayList<String> winners ;
    public EndGameCommand(boolean isCliClient, Game game,ArrayList winners, String username) {
        super(isCliClient, game, username);
        this.winners = (ArrayList<String>) winners;
    }

    @Override
    public void undo(Game game) throws EriantysExceptions {

    }

    @Override
    public void getData() throws EriantysExceptions {
        for (int i=0;i<winners.size();i++){
            System.out.println("winner is " + winners.get(i));
        }
        setDataGathered(true);
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        return true;
    }

}
