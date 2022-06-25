package it.polimi.ingsw.cestino.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character12 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private String msg ;


    public Character12(Game game) throws EriantysExceptions {

        msg = "Choose a type of Student: every player\n" +
                "(including yourself) must return 3 Students of that type\n" +
                "from their Dining Room to the bag. If any player has\n" +
                "fewer than 3 Students of that type, return as many\n" +
                "Students as they have.";
        coin = 3;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        for (int i=0;i< game.getN_Player();i++){
            int DiningRoomStudents=game.getPlayers().get(i).getPlayerBoard().getDiningRoom()[colore];
            for (int j=0;j<3 && DiningRoomStudents>0;j++){
                game.getPlayers().get(i).getPlayerBoard().takeStudentFromWaitingRoom(colore);
            }
        }
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
