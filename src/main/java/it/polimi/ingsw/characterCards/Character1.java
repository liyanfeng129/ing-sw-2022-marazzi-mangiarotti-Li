package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character1 implements CharacterBehavior{
    private int coin;
    private String msg;
    private boolean firstUse;
    private int students[]= new int[5];

    public Character1(Game game) throws EriantysExceptions {
        msg = "Take 1 Student from this card and place it on\n" +
                "an Island of your choice. Then, draw a new Student\n" +
                "from the Bag and place it on this card.";

        Bag bag  = game.getTable().getBag();

        coin = 1;
        firstUse = false;
        students = bag.draw(4);
        this.students = students;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore,int[] students) throws EriantysExceptions {

        if (!this.firstUse){
            this.firstUse = true;
        }
        Bag bag  = game.getTable().getBag();
        island.putStudent(colore);
        int temp [] = bag.draw(1);

        for(int i = 0 ; i < students.length; i++){
            this.students[i] = this.students[i] + temp[i];
        }

    }

    @Override
    public ArrayList getInfo() throws EriantysExceptions{
        ArrayList<Object> Attributes = new ArrayList<Object>();
        Attributes.add(this.getMsg());
        Attributes.add(this.getCoin());
        Attributes.add(this.isFirstUse());
        Attributes.add(this.getStudents());
        return Attributes;
    }





    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isFirstUse() {
        return firstUse;
    }

    public void setFirstUse(boolean firstUse) {
        this.firstUse = firstUse;
    }

    public int[] getStudents() {
        return students;
    }

    public void setStudents(int[] students) {
        this.students = students;
    }
}
