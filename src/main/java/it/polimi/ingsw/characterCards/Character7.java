package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character7 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private int students[]= new int[5];
    private String msg ;


    public Character7(Game game) throws EriantysExceptions {

        msg = "You may take up to 3 Students from this card\n" +
                "and replace them with the same number of Students\n" +
                "from your Entrance.";
        coin = 1;
        firstUse = false;
        Bag bag  = game.getTable().getBag();
        students = bag.draw(6);

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }

        //devo chiamare action phase1 o passare un array di 3 studenti
    }
    @Override
    public ArrayList getInfo() throws EriantysExceptions{
        ArrayList<Object> Attributes = new ArrayList<Object>();
        Attributes.add(this.getMsg());
        Attributes.add(this.getCoin());
        Attributes.add(this.isFirstUse());
        return Attributes;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}