package it.polimi.ingsw.command;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.state.PlanningState;
import it.polimi.ingsw.view.Cli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class GetAssistantCommand extends Command implements Serializable {
    private ArrayList<Assistant> assistants ; // pre_execute
    private ArrayList<Assistant> playedCard;
    Assistant assistant; // post_execute
    public GetAssistantCommand(ArrayList<Assistant> assistants,ArrayList<Assistant> playedCard,boolean isCliClient,Game game, String username)
    {
        super(isCliClient,game,username);
        this.assistants = (ArrayList<Assistant>) assistants.clone();
        this.playedCard = (ArrayList<Assistant>) playedCard.clone();
    }

    @Override
    public void undo(Game game) {

    }

    public void getData() {
        if(isCliClient())
        {
            int choice;
            do
            {
                System.out.println("Please select a assistant, choose from your hand");
                new Cli().show_Assistants(assistants);
                choice = new Scanner(System.in).nextInt();
                if(choice >= 1 && choice <= assistants.size())
                    for(Assistant as : playedCard)
                        if( as.getNum() == assistants.get(choice - 1).getNum())
                        {
                            System.out.println("\nCard already played, please play another one.\n");
                            choice = -1;
                        }
            }
            while(choice<1 || choice > assistants.size());
            assistant = assistants.get(choice-1);
            setDataGathered(true);
        }
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        if(!isDataGathered())
            return false;
        if(!assistants.contains(assistant))
            return false;
        if( game.getGameState() instanceof PlanningState)
        {
            ((PlanningState) game.getGameState()).addCard(assistant);
            game.findPlayerByName(getUsername()).getHand().use_cards(assistant.getType());
            game.findPlayerByName(getUsername()).getHand().setLastPlayedCard(assistant.getSteps());
            game.findPlayerByName(getUsername()).getHand().setLastValueAssistant(assistant.getNum());
            setMsg(String.format("Player %s used a card : %s",getUsername(), assistant.toString()));
            return true;
        }
        else
            return false;
    }
}
