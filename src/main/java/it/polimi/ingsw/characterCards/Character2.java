package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Character2 implements CharacterBehavior, Serializable {

    private int coin;
    private String msg;
    private boolean firstUse;

    public Character2(Game game) throws EriantysExceptions {
        msg = "During this turn, you take control of any\n" +
                "number of Professors even if you have the same\n" +
                "number of Students as the player who currently\n" +
                "controls them.";

        Bag bag  = game.getTable().getBag();

        coin = 2;
        firstUse = false;


    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore,int[] students) throws EriantysExceptions {
        /**TODO
         * add calcolo professor ogni turno del controller
         **/
        if (!this.firstUse){
            this.firstUse = true;
        }
        ArrayList<Player> players = game.getPlayers();
        Mage[] List_professors = game.getProfessors().getList_professors();
        int max=0;
        int tie=0;
        int assign=4;
        for(int i=0;i<5;i++){
            for (int j=0;j<players.size();j++){
                if(players.get(j).getPlayerBoard().getDiningRoom()[i] >= max) {
                    if(players.get(j).getPlayerBoard().getDiningRoom()[i] == max)
                        tie=1;
                    else
                        tie=0;
                    max = players.get(j).getPlayerBoard().getDiningRoom()[i];
                    assign=j;
                }
                if(tie!=1)
                    List_professors[i] = Mage.values()[assign];
                else
                    if (players.get(j)== user)
                        List_professors[i] = Mage.values()[assign];
                    else
                        List_professors[i] = Mage.values()[4];
            }
            max=0;
            tie=0;
            assign=4;
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
