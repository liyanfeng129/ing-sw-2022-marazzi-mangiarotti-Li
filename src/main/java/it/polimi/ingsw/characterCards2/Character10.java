package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class Character10 extends CharacterCard implements Serializable {
    private int dining_students[]= {0,0,0,0,0};
    private int entrance_student[]={0,0,0,0,0};
    int take_student=0;
    public Character10() {
        super();
        setCoin(1);
        String msg = "You may exchange up to 2 Students between\n" +
                "your Entrance and your Dining Room.";
        setMsg(msg);
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character10 card = (Character10) game.getTable().findCharacterCardByName(this.name());
        player.getPlayerBoard().removeStudentFromWaitingRoom(entrance_student);
        player.getPlayerBoard().removeStudentFromDiningRoom(dining_students);
        player.getPlayerBoard().addStudentsToDiningRoom(entrance_student);
        player.getPlayerBoard().addStudentsToWaitingRoom(dining_students);
        player.getWallet().removeCoin(card.getCoin());
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: playerboard had changed",
                player.getName(),this.name(), this.getCoin()));
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
            do {
                System.out.println("Do you want to exchange a student? 1 Yes");
                if(new Scanner(System.in).nextInt()==1){
                    System.out.println("Which student do you want to exchange, make sure that you have this student in your waiting room");
                    System.out.println("1: Red");
                    System.out.println("2: Yellow");
                    System.out.println("3: Pink");
                    System.out.println("4: Blue");
                    System.out.println("5: Green");
                    entrance_student[new Scanner(System.in).nextInt() - 1]++;
                    take_student++;
                    System.out.println("Which student do you want to exchange, make sure that you have this student on your dining room");
                    System.out.println("1: Red");
                    System.out.println("2: Yellow");
                    System.out.println("3: Pink");
                    System.out.println("4: Blue");
                    System.out.println("5: Green");
                    dining_students[new Scanner(System.in).nextInt() - 1]++;
                }
                else
                    break;
            }
            while (take_student<2);
            setDataGathered(true);
            game.getLastCommand().setDataGathered(true);
        }
        else
        {
            /**TODO
             * GUI get data
             * */
        }
        return true;
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