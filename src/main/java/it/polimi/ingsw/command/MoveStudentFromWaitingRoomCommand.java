package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.Cli;

import java.io.Serializable;
import java.util.Scanner;

public class MoveStudentFromWaitingRoomCommand extends Command implements Serializable {
    private int student;
    private int island_pos;
    private int[] waitingRoom;
    private boolean moveToIsland;


    // state
    public MoveStudentFromWaitingRoomCommand(int[] waitingRoom, boolean isCliClient, Game game,String username)
    {
        super(isCliClient,game,username);
        this.waitingRoom = waitingRoom;
    }

    public void execute(Game game, Player player) throws EriantysExceptions
    {
        if(isDataGathered())
        {
            if(moveToIsland)
                game.getTable().getIslands().get(island_pos).addStudent(student);
            else
                player.getPb().addStudentToHolder(student);
            player.getPb().takeStudentFromWaitingRoom(student);
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
                    System.out.println("Which student do you want to move, make sure that you have this student in your waiting room.");
                    System.out.println("1: Red");
                    System.out.println("2: Yellow");
                    System.out.println("3: Pink");
                    System.out.println("4: Blue");
                    System.out.println("5: Green");
                    choice = new Scanner(System.in).nextInt()-1;
                }
                while(waitingRoom[choice] == 0);
                student = choice;

                do
                {
                    System.out.println("Select where do you want to move the student.");
                    System.out.println("1: to an island");
                    System.out.println("2: to your student holder");
                    choice = new Scanner(System.in).nextInt();
                }
                while(!(choice == 1 || choice==2));
                moveToIsland = (choice == 1)? true : false;
                if(moveToIsland)
                {
                    int islands_size = getGame().getTable().getIslands().size();
                    do
                    {
                        System.out.println(String.format("Select one island from 1 to %d ", islands_size));
                        choice = new Scanner(System.in).nextInt();
                    }
                    while(choice < 1 || choice > islands_size);
                    island_pos = choice - 1;
                }
            }
        }
        setDataGathered(true);
    }

}
