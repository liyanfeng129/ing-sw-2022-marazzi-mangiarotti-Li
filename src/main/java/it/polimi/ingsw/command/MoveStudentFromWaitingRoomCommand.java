package it.polimi.ingsw.command;

import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SType;
import it.polimi.ingsw.state.ActionState;
import it.polimi.ingsw.view.Cli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveStudentFromWaitingRoomCommand extends Command implements Serializable {
    private int student;
    private int island_pos;
    private int[] waitingRoom;
    private boolean moveToIsland;
    private boolean characterCardUsed;
    private boolean characterCardExecuted;
    private boolean endGame=false;
    private int characterIndex = -1;


    // state
    public MoveStudentFromWaitingRoomCommand(int[] waitingRoom, boolean isCliClient, Game game, String username,boolean characterCardUsed,boolean characterCardExecuted) {
        super(isCliClient, game, username);
        this.waitingRoom = waitingRoom;
        this.characterCardUsed = characterCardUsed;
        this.characterCardExecuted = characterCardExecuted;
    }


    @Override
    public void undo(Game game) {

    }

    public void getData() {
        if (!isDataGathered()) {
            if (isCliClient()) {
                if(getGame().isExpertMode() && !characterCardUsed) {
                    int coin = getGame().getTurnList().get(getGame().getGameState().getPhase()).getWallet().getSaving();
                    int choice;
                    int quit=0;
                    System.out.println("Digit 10 if you want to use a character");
                    if (new Scanner(System.in).nextInt() == 10) {
                        /*
                        which character do you want to use
                        get this character
                        * */
                        Cli c = new Cli();
                        for (CharacterCard card : getGame().getTable().getCharacters())
                            c.show_character(card);
                            do {
                                System.out.println("Choose form 1 to 3, 10 to quit");
                                choice = new Scanner(System.in).nextInt() - 1;
                                if(choice==9) {
                                    quit=1;
                                    break;
                                }
                                else
                                    if (!(choice<0 || choice>2)) {
                                        characterIndex = choice;
                                        if (getGame().getTable().getCharacters().get(characterIndex).getCoin() < coin) {
                                            System.out.println("not enough money");
                                            continue;
                                        }
                                    }
                            } while (choice<0 || choice>2);
                        if(quit!=1)
                            characterCardUsed = true;
                        else
                            getDataForMoveStudent();
                    } else
                        getDataForMoveStudent();
                }
                else
                {
                    getDataForMoveStudent();
                }
            }
        }
        setDataGathered(true);
    }

    private void getDataForMoveStudent() {
        int choice;
        if (waitingRoom.length == 0)
            ((ActionState) getGame().getGameState()).setEndGame(true);
        else {
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

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        if(!characterCardUsed || characterCardExecuted)
            return normalExecute(game);
        else
            if(game.getGameState() instanceof ActionState)
            {
                ((ActionState)game.getGameState()).setCharacterCardUsed(characterCardUsed);
                ((ActionState)game.getGameState()).setCharacterIndex(characterIndex);
            }
        return true;
    }

    @Override
    public String GUIGetData(ArrayList<Object> inputs) {
        return null;
    }

    private boolean normalExecute(Game game) throws EriantysExceptions {
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
