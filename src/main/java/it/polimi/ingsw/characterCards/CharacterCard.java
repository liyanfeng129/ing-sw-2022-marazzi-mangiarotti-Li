package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;

public class CharacterCard implements Serializable {
    private CharacterBehavior CBehavior;

    public CharacterCard(CharacterBehavior CBehavior)
    {
        this.CBehavior = CBehavior;
    }

    public void useCard(Game game, Player user, Island island, int colore, Assistant assistant) throws EriantysExceptions {
        this.CBehavior.useCharacter(game, user,island,colore,assistant);
    }

}
