package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character9 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private String msg ;


    public Character9(Game game) throws EriantysExceptions {

        msg = "";
        coin = 3;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, Assistant assistant) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        game.getTable().setCard9(colore);
    }
}