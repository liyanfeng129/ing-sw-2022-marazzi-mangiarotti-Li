package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class ActionState extends State implements Serializable {

    private int numPlayers;
    public ActionState(Game game) {
        super(game);
        numPlayers = getGame().getN_Player();
    }

    @Override
    public void nextState() throws EriantysExceptions {

    }

    @Override
    public boolean canChangeState() throws EriantysExceptions {
        return getPhase() == numPlayers;
    }

    @Override
    public void executeCommand() throws EriantysExceptions {

    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        return null;
    }
}
