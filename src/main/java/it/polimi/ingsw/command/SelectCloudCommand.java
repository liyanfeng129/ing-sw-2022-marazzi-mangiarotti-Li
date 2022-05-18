package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.state.TakeCloudState;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class SelectCloudCommand extends Command implements Serializable{
    private int cloud;


    public SelectCloudCommand(boolean isCliClient, Game game, String username) {
        super(isCliClient, game, username);
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
                int n_clouds=getGame().getTable().getClouds().size();
                do
                {
                    System.out.println(String.format("Select one cloud from 1 to %d ", n_clouds));
                    choice = new Scanner(System.in).nextInt()-1;
                    if(Arrays.stream(getGame().getTable().getClouds().get(choice).getStudents()).sum() == 0)
                    {
                        System.out.println("Cloud is empty, please try again.");
                        choice = -1;
                    }
                }
                while(choice<0 || choice>n_clouds);
                cloud = choice;
                setDataGathered(true);
            }
        }

    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        if(!isDataGathered())
            return false;
        if(!(game.getGameState() instanceof TakeCloudState))
            return false;
        Player p = game.findPlayerByName(getUsername());
        Cloud c = game.getTable().getClouds().get(cloud);
        p.getPlayerBoard().addCloudToWaitingRoom(c.getStudents());
        c.emptyCloud();
        setMsg(String.format("Player %s took cloud %d", getUsername(), cloud + 1));
        return true;
    }
}

