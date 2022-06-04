package it.polimi.ingsw.state;
import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.GetAssistantCommand;
import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class PlanningState extends State implements Serializable {
    // condition for state to go next is when phase number equals to numPlayers,
    // means that we already executed all commands needed in this state
    ArrayList<Assistant> cards = new ArrayList<>();
    int numPlayers;
    public PlanningState(Game game, int phase) throws EriantysExceptions {
        super(game,phase);
        numPlayers = game.getN_Player();
    }

    @Override
    public void nextState() {

    }

    @Override
    public boolean canChangeState() {
        return getPhase() == numPlayers;
    } //

    @Override
    public void executeCommand() throws EriantysExceptions {
        if(getGame().getLastCommand().execute(getGame()))
            setPhase(getPhase()+1);

        if(canChangeState())
        {
            setActivePlayer();
            getGame().changeGameState(new ActionState(getGame(), 0));
        }
        getGame().removeCommand();
        getGame().addCommand(getGame().getGameState().generateCommand());
    }

    @Override
    public Command generateCommand() throws EriantysExceptions{
        if(!canChangeState())
        {
            ArrayList<Assistant> hand = getGame().getTurnList().get(getPhase()).getHand().getList_cards();
            String userName = getGame().getTurnList().get(getPhase()).getName();
            boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
            return new GetAssistantCommand(hand,cards,cliClient,getGame(),userName);
        }
        throw new InnerExceptions.PlanningStateError("cannot generate command.");
    }

    public void addCard(Assistant assistant)
    {
        this.cards.add(assistant);
    }
    public void setActivePlayer(){
        ArrayList<Player> playerQueue = new ArrayList<>();
        int min=getGame().getPlayers().get(0).getHand().getLastValueAssistant();
        int j=0;
        for (int i=0;i<getGame().getN_Player();i++){
            if (getGame().getPlayers().get(i).getHand().getLastValueAssistant()<min){
                min = getGame().getPlayers().get(0).getHand().getLastValueAssistant();
                j=i;
            }
        }
        for (int i=0;i< getGame().getN_Player();i++){
            if(j< getGame().getN_Player())
                playerQueue.add(getGame().getPlayers().get(j));
            else {
                playerQueue.add(getGame().getPlayers().get(0));
                j=0;
            }
            j++;
        }
        getGame().setTurnList(playerQueue);

    }
}
