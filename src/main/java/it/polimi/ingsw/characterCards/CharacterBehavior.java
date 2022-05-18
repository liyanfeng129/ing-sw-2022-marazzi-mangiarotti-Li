package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public interface CharacterBehavior extends Serializable {
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions;
    public ArrayList getInfo() throws EriantysExceptions;
}
