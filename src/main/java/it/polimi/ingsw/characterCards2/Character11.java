package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Character11 extends CharacterCard implements Serializable {
    private int[] students;
    int student_color = -1;
    public Character11(int [] students) {
        super();
        setCoin(2);
        setN_card(11);
        String msg = "Take 1 Student from this card and place it in\n" +
                "your Dining Room. Then, draw a new Student from the\n" +
                "Bag and place it on this card.";
        setMsg(msg);
        this.students = students;
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character11 card = (Character11) game.getTable().findCharacterCardByName(this.name());
        Bag bag  = game.getTable().getBag();
        player.getPb().addStudentToDiningRoom(student_color);
        int temp [];
        try {
            temp = bag.draw(1);
        } catch (EriantysExceptions e) {
            game.setStudentFinished(true);
            /*if this card is played in last turn due to not enough student left in bag,
             don t draw a card and play this turn with less choiche*/
            temp= new int[]{0, 0, 0, 0, 0};
        }
        card.takeStudent(student_color);
        card.addStudent(temp);
        player.getWallet().removeCoin(card.getCoin());
        game.getProfessors().assignProfessor(game.getPlayers());
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: one %s student has been moved to dining room",
                player.getName(),this.name(), this.getCoin() ,SType.values()[student_color].name()));
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
            System.out.println(getMsg() + "\n" +new Cli().show_students(students));
            int choice;

            do {
                System.out.println("Which student do you want to move, make sure that there this student on the card.");
                System.out.println("1: Red");
                System.out.println("2: Yellow");
                System.out.println("3: Pink");
                System.out.println("4: Blue");
                System.out.println("5: Green");
                choice = getInput() - 1;
            }
            while (students[choice] == 0);
            student_color = choice;
            setDataGathered(true);
            game.getLastCommand().setDataGathered(true);
        }
        return true;
    }

    /**
     * @param inputs
     * inputs.get(0) student_color : int
     *
     * input.get(1) game Game
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        Game game = (Game) inputs.get(1);
        student_color = (int) inputs.get(0);
        setDataGathered(true);
        game.getLastCommand().setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }

    @Override
    public String toString() {
        return "Character11{" +
                "dataGathered=" + isDataGathered() +
                ", students=" + Arrays.toString(students) +
                ", student_color=" + student_color +
                '}';
    }

    public String name()
    {
        return "Character11";
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
    public void takeStudent(int student_color){
        this.students[student_color] --;
    }

}

