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
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        int max_step=user.getHand().getLastPlayedCard()+2;
        user.getHand().setLastPlayedCard(max_step);
    }
}
