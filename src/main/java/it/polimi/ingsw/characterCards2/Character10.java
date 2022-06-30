package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Character10 extends CharacterCard implements Serializable {
    private int dining_students[];
    private int entrance_student[];
    int take_student=0;
    public Character10() {
        super();
        setCoin(1);
        setN_card(10);
        String msg = "You may exchange up to 2 Students between\n" +
                "your Entrance and your Dining Room.";
        setMsg(msg);
        this.dining_students= new int[]{0,0,0,0,0};
        this.entrance_student=new int[]{0,0,0,0,0};
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character10 card = (Character10) game.getTable().findCharacterCardByName(this.name());
        player.getPb().removeStudentFromWaitingRoom(entrance_student);
        player.getPb().removeStudentFromDiningRoom(dining_students);
        player.getPb().addStudentsToDiningRoom(entrance_student);
        player.getPb().addStudentsToWaitingRoom(dining_students);
        player.getWallet().removeCoin(card.getCoin());
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: playerboard had changed",
                player.getName(),this.name(), this.getCoin()));
        addCoin(player);
        if(card.isFirstUse())
            card.setFirstUse(false);
        return true;
    }

    @Override
    public boolean undoEffect(Game game, Player player) throws EriantysExceptions {
        // this character has permanent effect on game, nothing has to be undone.
        return true;
    }

    @Override
    public boolean getData(Game game, boolean isCliClient) throws EriantysExceptions {
        if(isCliClient)
        {
            int choice;
            Player player=game.getTurnList().get(game.getGameState().getPhase());
            do {
                System.out.println("Do you want to exchange a student? 1 Yes");
                int input = getInput();
                if(getInput()==1){
                    do {
                        System.out.println("Which student do you want to exchange, make sure that you have this student in your waiting room");
                        System.out.println("1: Red");
                        System.out.println("2: Yellow");
                        System.out.println("3: Pink");
                        System.out.println("4: Blue");
                        System.out.println("5: Green");
                        choice = getInput() - 1;
                    }while (player.getPb().getWaitingRoom()[choice]-entrance_student[choice] == 0);
                    entrance_student[choice]++;
                    do {
                        System.out.println("Which student do you want to exchange, make sure that you have this student on your dining room");
                        System.out.println("1: Red");
                        System.out.println("2: Yellow");
                        System.out.println("3: Pink");
                        System.out.println("4: Blue");
                        System.out.println("5: Green");
                        choice = getInput() - 1;

                    }while (player.getPb().getDiningRoom()[choice]-dining_students[choice] == 0);
                    dining_students[choice]++;
                    take_student++;
                }
                else
                    break;
            }
            while (take_student<2);
            setDataGathered(true);
            game.getLastCommand().setDataGathered(true);
        }
        return true;
    }

    /**
     * @param inputs
     * inputs.get(0) dining_students: int[]
     * inputs.get(1) entrance_student: int[]
     *
     * input.get(2) game Game
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        Game game = (Game) inputs.get(2);
        dining_students = (int[]) inputs.get(0);
        entrance_student = (int[]) inputs.get(1);
        setDataGathered(true);
        game.getLastCommand().setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }

    @Override
    public String toString() {
        return "Character10{" +
                "dataGathered=" + isDataGathered() +
                '}';
    }

    public String name()
    {
        return "Character10";
    }

}