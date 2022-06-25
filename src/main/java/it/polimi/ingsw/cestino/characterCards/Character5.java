package it.polimi.ingsw.cestino.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character5 implements CharacterBehavior {

    private int coin;
    private boolean firstUse;
    private String msg ;
    private int NoEntryTiles;


    public Character5(Game game) throws EriantysExceptions {

        msg = "Place a No Entry tile on an Island of your choice.\n" +
                "The first time Mother Nature ends her movement\n" +
                "there, put the No Entry tile back onto this card DO NOT\n" +
                "calculate influence on that Island, or place any Towers.";
        coin = 2;
        firstUse = false;
        NoEntryTiles=4;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        int pos=game.getTable().getIslands().indexOf(island);
        game.getTable().getIsland(pos).setNoEntryTiles(true);
        NoEntryTiles--;
        //bisogna aggiungere che se ce questa carta non si calcola influenza e merge
    }

    @Override
    public ArrayList getInfo() throws EriantysExceptions{
        ArrayList<Object> Attributes = new ArrayList<Object>();
        Attributes.add(this.getMsg());
        Attributes.add(this.getCoin());
        Attributes.add(this.isFirstUse());
        Attributes.add(this.getNoEntryTiles());
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

    public int getNoEntryTiles() {
        return NoEntryTiles;
    }

    public void setNoEntryTiles(int noEntryTiles) {
        NoEntryTiles = noEntryTiles;
    }
}

