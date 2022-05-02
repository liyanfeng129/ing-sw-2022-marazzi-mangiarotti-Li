package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.EriantysExceptions;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.Island;
import it.polimi.ingsw.Player;

public interface CharacterBehavior {
    public void useCharacter(Game game, Player user, Island island,int colore) throws EriantysExceptions;
}
