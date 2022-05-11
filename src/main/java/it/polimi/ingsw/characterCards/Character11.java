package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character11 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private String msg ;
    private int students[]= new int[5];


    public Character11(Game game) throws EriantysExceptions {

        msg = "";
        coin = 2;
        firstUse = false;
        students = game.getTable().getBag().draw(4);

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, Assistant assistant) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        user.getPlayerBoard().addStudentToHolder(colore);
        int[] temp=game.getTable().getBag().draw(1);
        for(int i = 0 ; i < students.length; i++){
            this.students[i] = this.students[i] + temp[i];
        }
    }
}
