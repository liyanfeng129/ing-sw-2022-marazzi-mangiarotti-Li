package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.Scanner;

public class SelectCloudCommand extends Command implements Serializable{
    private int cloud;


    public SelectCloudCommand(boolean isCliClient, Game game, String username) {
        super(isCliClient, game, username);
    }

    public void execute(Game game, Player player) throws EriantysExceptions
    {
        if(isDataGathered())
        {
            player.getPb().addCloudToWaitingRoom(game.getTable().getClouds().get(cloud).getStudents());
        }
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
                }
                while(choice<0 || choice>n_clouds);
                cloud = choice;
            }
        }
        setDataGathered(true);
    }
}

