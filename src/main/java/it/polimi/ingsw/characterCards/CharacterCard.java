package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class CharacterCard implements Serializable {
    private CharacterBehavior CBehavior;
    private int num;

    public CharacterCard(CharacterBehavior CBehavior)
    {
        this.CBehavior = CBehavior;
    }

    public void useCard(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        this.CBehavior.useCharacter(game, user,island,colore,students);
    }
    public  ArrayList getInfo() throws EriantysExceptions {
        return  this.CBehavior.getInfo();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
