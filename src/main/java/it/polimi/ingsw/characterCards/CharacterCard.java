package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;

import java.io.Serializable;

public class CharacterCard implements Serializable {
    private CharacterBehavior CBehavior;

    public CharacterCard(CharacterBehavior CBehavior)
    {
        this.CBehavior = CBehavior;
    }

    public void useCard(Game game, Player user)
    {
        this.CBehavior.useCharacter(game, user);
    }

}
