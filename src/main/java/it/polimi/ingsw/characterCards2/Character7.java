package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class Character7 extends CharacterCard implements Serializable {
    private int card_students[] = new int[5];
    private int students[]= {0,0,0,0,0,0};
    private int entrance_student[]={0,0,0,0,0,0};
    int take_student=0;
    public Character7(int [] students) {
        super();
        setCoin(1);
        String msg = "You may take up to 3 Students from this card\n" +
                "and replace them with the same number of Students\n" +
                "from your Entrance.";
        setMsg(msg);
        this.card_students = students;
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character7 card = (Character7) game.getTable().findCharacterCardByName(this.name());
        card.removeStudent(students);
        player.getPlayerBoard().removeStudentFromWaitingRoom(entrance_student);
        card.addStudent(entrance_student);
        player.getPlayerBoard().addStudentsToWaitingRoom(students);
        player.getWallet().removeCoin(card.getCoin());
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: entrance student have been replaced",
                player.getName(),this.name(), this.getCoin() ,students));
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
            System.out.println(getMsg() + "\n" +new Cli().show_students(card_students));
            do {
                System.out.println("Do you want to take a student? 1 Yes");
                if(new Scanner(System.in).nextInt()==1){
                    System.out.println("Which student do you want to exchange, make sure that you have this student in your waiting room");
                    System.out.println("1: Red");
                    System.out.println("2: Yellow");
                    System.out.println("3: Pink");
                    System.out.println("4: Blue");
                    System.out.println("5: Green");
                    entrance_student[new Scanner(System.in).nextInt() - 1]++;
                    take_student++;
                    System.out.println("Which student do you want to exchange, make sure that there is this student on this card");
                    System.out.println("1: Red");
                    System.out.println("2: Yellow");
                    System.out.println("3: Pink");
                    System.out.println("4: Blue");
                    System.out.println("5: Green");
                    students[new Scanner(System.in).nextInt() - 1]++;
                }
                else
                    break;
            }
            while (take_student<3);
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
        return "Character7{" +
                "dataGathered=" + isDataGathered() +
                ", students=" + Arrays.toString(students) +
                '}';
    }

    public String name()
    {
        return "Character7";
    }

    public int[] getStudents() {
        return students;
    }

    public void setStudents(int[] students) {
        this.students = students;
    }

    public void addStudent(int[] students)
    {
        for(int i = 0; i < 5; i++)
            this.students[i] += students[i];
    }
    public void removeStudent(int student[]){
        for(int i = 0; i < 5; i++)
            this.card_students[i] -= students[i];;
    }
}

