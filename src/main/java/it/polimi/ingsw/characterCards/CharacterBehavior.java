package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

public interface CharacterBehavior {
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions;
}
