package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public class CharacterCard implements Serializable {
    private CharacterBehavior CBehavior;

    public CharacterCard(CharacterBehavior CBehavior)
    {
        this.CBehavior = CBehavior;
    }

    public void useCard(Game game, Player user, Island island,int colore) throws EriantysExceptions {
        this.CBehavior.useCharacter(game, user,island,colore);
    }

}
