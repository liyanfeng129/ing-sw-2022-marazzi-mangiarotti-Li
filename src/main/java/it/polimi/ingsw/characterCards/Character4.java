package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

public class Character4 implements CharacterBehavior{
    private int coin;
    private boolean firstUse;
    private String msg ;

    public Character4(Game game) throws EriantysExceptions {

        msg = "";
        coin = 1;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, Assistant assistant) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        int max_step=assistant.getSteps()+2;
    //come lo passo al controller?
    }
}
