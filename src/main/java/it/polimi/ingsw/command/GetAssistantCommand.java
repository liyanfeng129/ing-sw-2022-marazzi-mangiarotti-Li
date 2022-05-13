package it.polimi.ingsw.command;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.state.PlanningState;
import it.polimi.ingsw.view.Cli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class GetAssistantCommand extends Command implements Serializable {
    ArrayList<Assistant> assistants; // pre_execute
    Assistant assistant; // post_execute
    public GetAssistantCommand(ArrayList<Assistant> assistants,boolean isCliClient,Game game, String username)
    {
        super(isCliClient,game,username);
        this.assistants = (ArrayList<Assistant>) assistants.clone();
    }

    @Override
    public void undo() {

    }

    public void getData() {
        AssistantType type  = assistants.get(0).getAssistantType();
        int choice;
        do
        {
            System.out.println("Please select a assistant, choose from your hand");
            new Cli().show_Assistants(assistants);
            choice = new Scanner(System.in).nextInt();
        }
        while(choice<0 || choice > assistants.size());


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
            game.findPlayerByName(getUsername()).getHand().use_cards(assistant.getAssistantType());
            game.findPlayerByName(getUsername()).getHand().setLastPlayedCard(assistant.getSteps());
            return true;
        }
        else
            return false;
    }


    public ArrayList<Assistant> gettingCondition(){
        return assistants;
    }



}
