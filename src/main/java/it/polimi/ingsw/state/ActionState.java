package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.GetAssistantCommand;
import it.polimi.ingsw.command.MoveStudentFromWaitingRoomCommand;
import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;
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
        {
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
        }
        if(canChangeState())
        {
            getGame().changeGameState(new MoveMotherNatureState(getGame(), getPhase()));
            //getGame().addCommand(getGame().getGameState().generateCommand());
        }
            getGame().addCommand(getGame().getGameState().generateCommand());
    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        if(!canChangeState())
        {
            int[] waitingRoom = getGame().getTurnList().get(getPhase()).getPlayerBoard().getWaitingRoom();
            String userName = getGame().getTurnList().get(getPhase()).getName();
            boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
            return new MoveStudentFromWaitingRoomCommand(waitingRoom,cliClient,getGame(),userName);
        }
        throw new InnerExceptions.PlanningSteteError("cannot generate command.");
    }

    public boolean isOnIsland() {
        return onIsland;
    }

    public void setOnIsland(boolean onIsland) {
        this.onIsland = onIsland;
    }
}
