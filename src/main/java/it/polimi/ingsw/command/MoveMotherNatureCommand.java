package it.polimi.ingsw.command;

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

    @Override
    public String GUIGetData(ArrayList<Object> inputs) {
        return null;
    }
}
