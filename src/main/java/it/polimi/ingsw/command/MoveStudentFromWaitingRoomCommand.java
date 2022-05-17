package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SType;
import it.polimi.ingsw.state.ActionState;
import it.polimi.ingsw.view.Cli;

import java.io.Serializable;
import java.util.Scanner;

public class MoveStudentFromWaitingRoomCommand extends Command implements Serializable {
    private int student;
    private int island_pos;
    private int[] waitingRoom;
    private boolean moveToIsland;


    // state
    public MoveStudentFromWaitingRoomCommand(int[] waitingRoom, boolean isCliClient, Game game, String username) {
        super(isCliClient, game, username);
        this.waitingRoom = waitingRoom;
    }


    @Override
    public void undo() {

    }

    public void getData() {
        if (!isDataGathered()) {
            if (isCliClient()) {
                int choice;

                do {
                    System.out.println("Which student do you want to move, make sure that you have this student in your waiting room.");
                    System.out.println("1: Red");
                    System.out.println("2: Yellow");
                    System.out.println("3: Pink");
                    System.out.println("4: Blue");
                    System.out.println("5: Green");
                    choice = new Scanner(System.in).nextInt() - 1;
                }
                while (waitingRoom[choice] == 0);
                student = choice;

                do {
                    System.out.println("Select where do you want to move the student.");
                    System.out.println("1: to an island");
                    System.out.println("2: to your student holder");
                    choice = new Scanner(System.in).nextInt();
                }
                while (!(choice == 1 || choice == 2));
                moveToIsland = (choice == 1) ? true : false;
                if (moveToIsland) {
                    int islands_size = getGame().getTable().getIslands().size();
                    do {
                        System.out.println(String.format("Select one island from 1 to %d ", islands_size));
                        choice = new Scanner(System.in).nextInt();
                    }
                    while (choice < 1 || choice > islands_size);
                    island_pos = choice - 1;
                }
            }
        }
        setDataGathered(true);
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        if(game.getGameState() instanceof ActionState)
        {
            Player p = game.findPlayerByName(getUsername());
            if (isDataGathered())
            {
                if (moveToIsland)
                {
                    game.getTable().getIslands().get(island_pos).addStudent(student);
                    setMsg(String.format("Player %s moved a %s student on island %d",
                            getUsername(), SType.values()[student].name().toString(), island_pos + 1 ));
                }
                else
                {
                    p.getPb().addStudentToHolder(student);
                    setMsg(String.format("Player %s moved a %s student on his dining room.",getUsername(),SType.values()[student].name().toString()));
                }

                //aggiungo coin
                if(getGame().isExpertMode()) {
                    if (p.getPb().getDiningRoom()[student] == 3 && p.getPb().getCoin3()[student] == false) {
                        p.getWallet().addCoin(1);
                        p.getPb().setCoin3(student);
                    }
                    if (p.getPb().getDiningRoom()[student] == 6 && p.getPb().getCoin6()[student] == false) {
                        p.getWallet().addCoin(1);
                        p.getPb().setCoin6(student);
                    }
                    if (p.getPb().getDiningRoom()[student] == 9 && p.getPb().getCoin9()[student] == false) {
                        p.getWallet().addCoin(1);
                        p.getPb().setCoin9(student);
                    }
                }
                p.getPb().takeStudentFromWaitingRoom(student);
                ((ActionState) game.getGameState()).setOnIsland(moveToIsland);
                return true;
            }
        }

        return false;

    }
}
