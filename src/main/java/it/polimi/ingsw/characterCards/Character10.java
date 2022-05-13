package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character10 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private String msg ;


    public Character10(Game game) throws EriantysExceptions {

        msg = "";
        coin = 1;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        //stesso problema carta 7
    }
}