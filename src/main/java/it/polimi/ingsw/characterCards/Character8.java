package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character8 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private String msg ;


    public Character8(Game game) throws EriantysExceptions {

        msg = "";
        coin = 2;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        game.getTable().setCard8(user.getName());
        //stesso problema carta 6
    }
}
