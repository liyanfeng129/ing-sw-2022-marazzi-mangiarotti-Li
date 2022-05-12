package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class MoveMotherNatureState extends State implements Serializable {
    private int maxSteps;
    public MoveMotherNatureState(Game game, int phase) {
        super(game, phase);
    }

    @Override
    public void nextState() throws EriantysExceptions {

    }

    @Override
    public boolean canChangeState() throws EriantysExceptions {
        return false;
    }

    @Override
    public void executeCommand() throws EriantysExceptions {

    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        return null;
    }
}
