package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character6 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private String msg ;


    public Character6(Game game) throws EriantysExceptions {

        msg = "When resolving a Conquering on an Island,\n" +
                "Towers do not count towards influence.";
        coin = 3;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        game.getTable().setCard6(true);
        //potrebbero esserci problemi con altre carte
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