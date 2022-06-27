package it.polimi.ingsw.cestino.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character11 implements CharacterBehavior {

    private int coin;
    private boolean firstUse;
    private String msg ;
    private int students[]= new int[5];


    public Character11(Game game) throws EriantysExceptions {

        msg = "Take 1 Student from this card and place it in\n" +
                "your Dining Room. Then, draw a new Student from the\n" +
                "Bag and place it on this card.";
        coin = 2;
        firstUse = false;
        students = game.getTable().getBag().draw(4);

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        user.getPb().addStudentToDiningRoom(colore);
        int[] temp=game.getTable().getBag().draw(1);
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

    public boolean isFirstUse() {
        return firstUse;
    }

    public void setFirstUse(boolean firstUse) {
        this.firstUse = firstUse;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int[] getStudents() {
        return students;
    }

    public void setStudents(int[] students) {
        this.students = students;
    }
}
