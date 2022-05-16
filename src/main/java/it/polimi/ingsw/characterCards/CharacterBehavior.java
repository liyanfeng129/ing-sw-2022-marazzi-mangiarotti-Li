package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public interface CharacterBehavior {
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions;
    public ArrayList getInfo() throws EriantysExceptions;
}
