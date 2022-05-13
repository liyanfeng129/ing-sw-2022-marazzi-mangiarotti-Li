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
    public PlanningState(Game game, int phase) {
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
            /**
             * TODO
             * implement necessary things before change game state
             * */
            setActivePlayer();
            getGame().changeGameState(new ActionState(getGame(), 0));
        }
        getGame().addCommand(getGame().getGameState().generateCommand());
    }

    @Override
    public Command generateCommand() throws EriantysExceptions{
        if(!canChangeState())
        {
            ArrayList<Assistant> hand = getGame().getTurnList().get(getPhase()).getHand().getList_cards();
            String userName = getGame().getTurnList().get(getPhase()).getName();
            boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
            return new GetAssistantCommand(hand,cliClient,getGame(),userName);
        }
        throw new InnerExceptions.PlanningSteteError("cannot generate command.");
    }

    public void addCard(Assistant assistant)
    {
        this.cards.add(assistant);
    }
    public void setActivePlayer(){
        ArrayList<Player> playerQueue = new ArrayList<>();
        for(Player p : getGame().getPlayers())
            playerQueue.add(p);
        /*
        int min=cards.get(0).getNum();
        int j=0;
        for (int i=0;i<cards.size();i++){
            if (cards.get(i).getNum()<min){
                min = cards.get(i).getNum();
                j=i;
            }
        }
        for (int i=0;i< getGame().getN_Player();i++){
            if((j+1)< getGame().getN_Player())
                playerQueue.add(getGame().getPlayers().get(j));
            else
                playerQueue.add(getGame().getPlayers().get(0));
            j++;
        }
         */
        int i = 0;
        int min = playerQueue.get(0).getHand().getLastPlayedCard();
        for(; i < playerQueue.size(); i++)

        getGame().setTurnList(playerQueue);

    }
}
