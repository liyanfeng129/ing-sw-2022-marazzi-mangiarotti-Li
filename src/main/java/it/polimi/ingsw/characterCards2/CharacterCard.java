package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class CharacterCard implements Serializable {
    private int coin;
    private String msg;
    private boolean firstUse;
    private boolean dataGathered;
    private int N_card;

    public CharacterCard() {
        firstUse = true;
        dataGathered = false;
    }
    public abstract boolean useCard(Game game, Player player) throws EriantysExceptions;
    public abstract boolean undoEffect(Game game, Player player) throws EriantysExceptions;
    public abstract boolean getData(Game game, boolean isCliClient)throws EriantysExceptions;
    public abstract String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions;
    public abstract String name();

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

    public boolean isDataGathered() {
        return dataGathered;
    }

    public void setDataGathered(boolean dataGathered) {
        this.dataGathered = dataGathered;
    }

    public int getN_card() {
        return N_card;
    }

    public void setN_card(int n_card) {
        N_card = n_card;
    }

    public void addCoin(Player p){
        for (int i=0;i<5;i++) {
            if (p.getPb().getDiningRoom()[i] >= 3 && p.getPb().getCoin3()[i] == false) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin3(i);
            }
            if (p.getPb().getDiningRoom()[i] >= 6 && p.getPb().getCoin6()[i] == false) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin6(i);
            }
            if (p.getPb().getDiningRoom()[i] >= 9 && p.getPb().getCoin9()[i] == false) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin9(i);
            }
        }
    }
}
