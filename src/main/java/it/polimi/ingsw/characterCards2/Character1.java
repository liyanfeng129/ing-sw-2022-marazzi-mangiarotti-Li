package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Character1 extends CharacterCard implements Serializable {
    private int[] students;
    int student_color = -1;
    int island_pos = -1;

    /**
     * costructor for character1
     * @param students the student placed on this card at the start of the game
     */
    public Character1(int [] students) {
        super();
        setN_card(1);
        setCoin(1);
        String msg = "Take 1 Student from this card and place it on\n" +
                "an Island of your choice. Then, draw a new Student\n" +
                "from the Bag and place it on this card.";
        setMsg(msg);
        this.students = students;
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character1 card = (Character1) game.getTable().findCharacterCardByName(this.name());
        Bag bag  = game.getTable().getBag();
        game.getTable().getIsland(island_pos).addStudent(student_color);
        //if there is only a student left means that this is the last turn because this card draw it
        if(Arrays.stream(game.getTable().getBag().getBag()).sum()==1)
            game.setStudentFinished(true);
        int temp [];
        try {
            temp = bag.draw(1);
        } catch (EriantysExceptions e) {
            /*if this card is played in last turn due to not enough student left in bag,
             don t draw a card and play this turn with less choiche*/
            temp= new int[]{0, 0, 0, 0, 0};
        }
        card.takeStudent(student_color);
        card.addStudent(temp);
        player.getWallet().removeCoin(card.getCoin());
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: one %s student has been moved to island %d",
                player.getName(),this.name(), this.getCoin() ,SType.values()[student_color].name(), island_pos
        ));
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
                System.out.println("Which student do you want to move, make sure that you have this student in your waiting room.");
                System.out.println("1: Red");
                System.out.println("2: Yellow");
                System.out.println("3: Pink");
                System.out.println("4: Blue");
                System.out.println("5: Green");
                choice = getInput() - 1;
            }
            while (students[choice] == 0);
            student_color = choice;
            int islands_size = game.getTable().getIslands().size();
            do {
                System.out.println(String.format("Select one island from 1 to %d ", islands_size));
                choice = getInput();
            }
            while (choice < 1 || choice > islands_size);
            island_pos = choice - 1;
            setDataGathered(true);
            game.getLastCommand().setDataGathered(true);
        }
        return true;
    }

    /**
     * @param inputs
     * inputs.get(0) = student: int
     * inputs.get(1) = island_index: int
     *
     * inputs.get(2) = game : Game
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        student_color = (int) inputs.get(0);
        island_pos = (int) inputs.get(1);
        Game game = (Game) inputs.get(2);
        game.getLastCommand().setDataGathered(true);
        setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }

    @Override
    public String toString() {
        return "Character1{" +
                "dataGathered=" + isDataGathered() +
                ", students=" + Arrays.toString(students) +
                ", student_color=" + student_color +
                ", island_pos=" + island_pos +
                '}';
    }

    public String name()
    {
        return "Character1";
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
