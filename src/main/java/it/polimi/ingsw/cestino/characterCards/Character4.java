package it.polimi.ingsw.cestino.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character4 implements CharacterBehavior {
    private int coin;
    private boolean firstUse;
    private String msg ;

    public Character4(Game game) throws EriantysExceptions {

        msg = "You may move Mother Nature up to 2\n" +
                "additional Islands than is indicated by the Assistant\n" +
                "card you've played.";
        coin = 1;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        int max_step=user.getHand().getLastStepsAssistant()+2;
        user.getHand().setLastStepsAssistant(max_step);
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
