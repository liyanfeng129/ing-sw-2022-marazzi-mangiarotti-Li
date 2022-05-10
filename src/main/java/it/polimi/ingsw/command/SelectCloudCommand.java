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

            }
        }
        setDataGathered(true);
    }
}

