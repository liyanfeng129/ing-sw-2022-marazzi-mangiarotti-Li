package it.polimi.ingsw.command;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class GetAssistantCommand extends Command implements Serializable {
    ArrayList<Assistant> assistants;
    Assistant assistant;
    public GetAssistantCommand(ArrayList<Assistant> assistants,boolean isCliClient,Game game, String username)
    {
        super(isCliClient,game,username);
        this.assistants = (ArrayList<Assistant>) assistants.clone();
    }

    public void fillingData(Assistant assistant) {
        this.assistant = assistant;
    }

    public ArrayList<Assistant> gettingCondition(){
        return assistants;
    }



    public boolean execute(Game game) {
       if(!isDataGathered())
            return false;
       else
       {

           return true;
       }
    }

}
