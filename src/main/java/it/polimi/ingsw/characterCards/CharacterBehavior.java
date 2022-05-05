package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;

public interface CharacterBehavior {
    public void useCharacter(Game game, Player user, Island island,int colore) throws EriantysExceptions;
}
