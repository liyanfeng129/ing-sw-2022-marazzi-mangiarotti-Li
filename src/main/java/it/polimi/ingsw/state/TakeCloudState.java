package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.MoveStudentFromWaitingRoomCommand;
import it.polimi.ingsw.command.SelectCloudCommand;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.InnerExceptions;
import it.polimi.ingsw.model.InnerExceptions.NotEnoughStudentsInBagException;

import java.io.Serializable;

public class TakeCloudState extends State implements Serializable {
    private boolean can=false;

    public TakeCloudState(Game game, int phase) {
        super(game, phase);
    }

    @Override
    public void nextState() throws EriantysExceptions {

    }

    @Override
    public boolean canChangeState() throws EriantysExceptions {
        if(isCan())
            return true;
        else
            return false;
    }

    @Override
    public void executeCommand() throws EriantysExceptions {
        if(getGame().getLastCommand().execute(getGame()))
            setCan(true);
        if(canChangeState())
        {
            if (getPhase()==getGame().getN_Player()-1)
            {
                try
                {
                    getGame().getTable().initClouds();
                    if (getGame().getPlayers().get(0).getHand().getN_cards()==0)
                        getGame().changeGameState(new EndGameState(getGame(),getPhase()));
                    else
                        getGame().changeGameState(new PlanningState(getGame(), 0));
                }
                catch (InnerExceptions.NotEnoughStudentsInBagException e)
                {

                }

            }
            else
                getGame().changeGameState(new ActionState(getGame(),getPhase()+1));
        }
        getGame().removeLastCommand();
        getGame().addCommand(getGame().getGameState().generateCommand());
    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        if(!canChangeState())
        {
            String userName = getGame().getTurnList().get(getPhase()).getName();
            boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
            return new SelectCloudCommand(cliClient,getGame(),userName);
        }
        throw new InnerExceptions.PlanningSteteError("cannot generate command.");
    }
    public boolean isCan() {
        return can;
    }

    public void setCan(boolean can) {
        this.can = can;
    }
}
