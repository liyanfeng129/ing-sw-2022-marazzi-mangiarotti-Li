package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.Arrays;

public class Character3 implements CharacterBehavior{
    private int coin;
    private boolean firstUse;
    private String msg ;

    public Character3(Game game) throws EriantysExceptions {

        msg = "Choose an Island and resolve the Island as if\n" +
                "Mother Nature had ended her movement there. Mother\n" +
                "Nature will still move and the Island where she ends\n" +
                "her movement will also be resolved.";
        coin = 1;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore) throws EriantysExceptions {
        Island mn_prec = game.getTable().getIslands().get(game.getTable().getMotherNatureIndex());

        game.getTable().moveMotherNature(game.getTable().getIslands().indexOf(island));




        if (game.getTable().getPlayerMaxInfluence(game)==user){

        }

    }


}
