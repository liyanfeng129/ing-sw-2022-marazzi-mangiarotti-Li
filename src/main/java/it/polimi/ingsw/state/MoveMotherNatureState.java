package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.MoveMotherNatureCommand;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.InnerExceptions;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public class MoveMotherNatureState extends State implements Serializable {
    private int maxSteps;
    private boolean can=false;
    private boolean GameEnded=false;
    public MoveMotherNatureState(Game game, int phase) {
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
        if(getGame().getLastCommand().execute(getGame()));
        {
            Player player=getGame().getTable().getPlayerMaxInfluence(getGame());
            //condizione endGame finite torri in pb
            if (player!=null){
                for (int i=0;i<getGame().getN_Player();i++){
                    if(getGame().getTurnList().get(i).getPlayerBoard().getN_tower()==0){
                        setGameEnded(true);
                        //devo passargli anche chi ha vinto?
                    }
                }
                getGame().getTable().mergeIsland();
                if (getGame().getTable().getIslands().size()<=3){
                    setGameEnded(true);
                }
            }
            setCan(true);
            /**
             * TODO se giocatore dovesse giocare characther calcola influenza dove vuoi
             * allora setcan=false e genera nuovo comando;
             */
            if (canChangeState()) {
                if (!isGameEnded()) {
                    getGame().changeGameState(new TakeCloudState(getGame(), getPhase()));
                }
                else{
                    getGame().changeGameState(new EndGameState(getGame(), getPhase()));
                }
            }
            getGame().removeCommand();
            getGame().addCommand(getGame().getGameState().generateCommand());
        }
    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        if(!canChangeState())
        {
            int maxSteps = getGame().getTurnList().get(getPhase()).getHand().getLastPlayedCard();
            String userName = getGame().getTurnList().get(getPhase()).getName();
            boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
            return new MoveMotherNatureCommand(cliClient,getGame(),userName,maxSteps);
        }
        throw new InnerExceptions.PlanningSteteError("cannot generate command.");
    }

    public boolean isCan() {
        return can;
    }

    public void setCan(boolean can) {
        this.can = can;
    }

    public boolean isGameEnded() {
        return GameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        GameEnded = gameEnded;
    }
}


