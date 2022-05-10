package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.Scanner;

public class MoveMotherNatureCommand extends Command implements Serializable {
    private int steps;
    private int maxSteps;


    public MoveMotherNatureCommand(boolean isCliClient, Game game, String username,int maxSteps) {
        super(isCliClient, game, username);
        this.maxSteps = maxSteps;
    }

    public void execute(Game game, Player player) throws EriantysExceptions
    {
        if(isDataGathered())
        {
            game.getTable().moveMotherNature(steps);
        }
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
                    System.out.println("How many steps do you want to take?.");
                    choice = new Scanner(System.in).nextInt();
                }
                while(choice<0 || choice>maxSteps);
                steps = choice;
            }
        }
        setDataGathered(true);
    }
}
