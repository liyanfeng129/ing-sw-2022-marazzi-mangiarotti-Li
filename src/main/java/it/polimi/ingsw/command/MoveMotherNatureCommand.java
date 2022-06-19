package it.polimi.ingsw.command;

import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveMotherNatureCommand extends Command implements Serializable {
    private int steps;
    private int maxSteps;


    public MoveMotherNatureCommand(boolean isCliClient, Game game, String username,int maxSteps) {
        super(isCliClient, game, username);
        this.maxSteps = maxSteps;
    }


    @Override
    public void undo(Game game) {

    }

    public void  getData()
    {
        if(!isDataGathered())
        {
            if(isCliClient())
            {
                int choice;
                do
                {
                    System.out.println("How many steps do you want to take?.\n"+
                            "You have "+maxSteps+" steps");
                    choice = new Scanner(System.in).nextInt();
                }
                while(choice < 1 || choice >   maxSteps);
                steps = choice;
                setDataGathered(true);
            }
        }
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        if(isDataGathered())
        {
            game.getTable().moveMotherNature(steps);
            setMsg(String.format("Player %s moved the mother nature %d steps in forward.", getUsername(), steps));
            return true;
        }
        return false;
    }

    /**
     * @param inputs
     * inputs.get(0) steps: int (selected island position - island position where lands mother nature)
     * @return
     * Config.GUI_COMMAND_GETDATA_SUC  if ok
     * Config.GUI_WRONG_STEPS if ko
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) {
        int steps = (int) inputs.get(0);
        if(steps > maxSteps)
            return Config.GUI_WRONG_STEPS;
        this.steps = steps;
        setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }
}
