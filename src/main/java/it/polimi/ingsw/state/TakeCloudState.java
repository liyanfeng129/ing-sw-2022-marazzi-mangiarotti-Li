package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.SelectCloudCommand;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.InnerExceptions;

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
                    {
                        getGame().changeGameState(new PlanningState(getGame(), 0));
                        /*
                         * if someone used a character card in previous turn
                         * undo the temporary effect caused by the card
                         * reset the card
                         * */
                        if(getGame().getUsedCharacter() != null)
                        {
                            getGame().getUsedCharacter().undo(getGame());
                            getGame().setUsedCharacter(null);
                        }
                    }

                }
                catch (InnerExceptions.NotEnoughStudentsInBagException e)
                {
                    getGame().changeGameState(new EndGameState(getGame(),0));
                }

            }
            else
                getGame().changeGameState(new ActionState(getGame(),getPhase()+1));
        }
        getGame().removeCommand();
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
        throw new InnerExceptions.PlanningStateError("cannot generate command.");
    }
    public boolean isCan() {
        return can;
    }

    public void setCan(boolean can) {
        this.can = can;
    }
}
