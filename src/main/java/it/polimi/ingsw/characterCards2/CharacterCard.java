package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public abstract class CharacterCard implements Serializable {
    private int coin;
    private String msg;
    private boolean firstUse;

    public CharacterCard() {

    }
    public abstract boolean useCard(Game game, Player player) throws EriantysExceptions;
    public abstract boolean undoEffect(Game game, Player player) throws EriantysExceptions;
    public abstract boolean getData()throws EriantysExceptions;

    public int getCoin() {
        return coin+(firstUse? 0:1);
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
}
