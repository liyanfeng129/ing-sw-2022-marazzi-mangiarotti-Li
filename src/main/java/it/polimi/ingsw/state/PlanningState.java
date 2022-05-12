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
    public PlanningState(Game game) {
        super(game);
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
        if(getGame().getLastCommand().execute(getGame()));
            setPhase(getPhase()+1);

        if(canChangeState())
        {
            /**
             * TODO
             * implement necessary things before change game state
             * */
            getGame().changeGameState(new ActionState(getGame()));
        }
    }

    @Override
    public Command generateCommand() throws EriantysExceptions{
        if(!canChangeState())
        {
            ArrayList<Assistant> hand = getGame().getPlayers().get(getPhase()).getHand().getList_cards();
            String userName = getGame().getPlayers().get(getPhase()).getName();
            boolean cliClient = getGame().getPlayers().get(getPhase()).isCliClient();
            return new GetAssistantCommand(hand,cliClient,getGame(),userName);
        }
        throw new InnerExceptions.PlanningSteteError("cannot generate command.");
    }

    public void addCard(Assistant assistant)
    {
        this.cards.add(assistant);
    }
}
