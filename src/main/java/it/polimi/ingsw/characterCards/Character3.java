package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Character3 implements CharacterBehavior, Serializable {
    private int coin;
    private boolean firstUse;
    private String msg ;

    public Character3(Game game) throws EriantysExceptions {

        msg = "Choose an Island and resolve the Island as if\n" +
                "Mother Nature had ended her movement there. Mother\n" +
                "Nature will still move and the Island where she ends\n" +
                "her movement will also be resolved.";
        coin = 3;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore,int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        int MN_prec=game.getTable().getMotherNatureIndex();
        game.getTable().getIsland(MN_prec).setMotherNature(false);
        int MN_temp=game.getTable().getIslands().indexOf(island);
        game.getTable().getIsland(MN_temp).setMotherNature(true);
        for (int i = 0; i < game.getN_Player(); i++) {
            if (game.getTable().getPlayerMaxInfluence(game) == game.getPlayers().get(i))
                game.getTable().getIsland(game.getTable().getMotherNatureIndex()).setTower(game.getPlayers().get(i).getTowerColor());
        }
        game.getTable().mergeIsland();
        //dove si sposta madre natura in caso di merge?
        game.getTable().getIsland(MN_temp).setMotherNature(false);
        game.getTable().getIsland(MN_prec).setMotherNature(true);
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
