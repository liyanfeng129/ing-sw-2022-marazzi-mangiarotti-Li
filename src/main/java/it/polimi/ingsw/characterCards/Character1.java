package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;

public class Character1 implements CharacterBehavior{
    private int coin;
    private boolean firstUse;
    private int students[];

    public Character1(int[] students)
    {
        coin = 1;
        firstUse = false;
        students = new int[5];
        this.students = students;

    }

    @Override
    public void useCharacter(Game game, Player user) {

    }
}
