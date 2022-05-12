package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerBoard;

import java.io.Serializable;
import java.util.Arrays;

public class ActionState extends State implements Serializable {

    private int numStudents;
    private boolean onIsland;
    private boolean expertMode;
    public ActionState(Game game, int phase) {
        super(game,phase);
        numStudents = getGame().getTable().getClouds().get(0).getSize();
        expertMode = getGame().isExpertMode();
    }

    @Override
    public void nextState() throws EriantysExceptions {

    }

    @Override
    public boolean canChangeState() throws EriantysExceptions {
        boolean can = false;
        PlayerBoard pb = getGame().getPlayers().get(getPhase()).getPlayerBoard();
        if(Arrays.stream(pb.getWaitingRoom()).sum() == pb.getMaxStudentsInWaiting() - numStudents )
            can = true;
        return can;
    }

    @Override
    public void executeCommand() throws EriantysExceptions {
        if(getGame().getLastCommand().execute(getGame()))
            if(!onIsland )
            {
                getGame().getProfessors().assignProfessor(getGame().getPlayers());
                if(expertMode)
                {
                    /**TODO
                     * Ass
                     * wallet, coin
                     * */
                }
            }
        if(canChangeState())
        {
            getGame().changeGameState(new MoveMotherNatureState(getGame(), getPhase()));
        }
            getGame().addCommand(getGame().getGameState().generateCommand());
    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        return null;
    }

    public boolean isOnIsland() {
        return onIsland;
    }

    public void setOnIsland(boolean onIsland) {
        this.onIsland = onIsland;
    }
}
