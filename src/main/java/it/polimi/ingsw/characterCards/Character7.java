package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character7 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private int students[]= new int[5];
    private String msg ;


    public Character7(Game game) throws EriantysExceptions {

        msg = "";
        coin = 1;
        firstUse = false;
        Bag bag  = game.getTable().getBag();
        students = bag.draw(6);

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }

        //devo chiamare action phase1 o passare un array di 3 studenti
    }
}