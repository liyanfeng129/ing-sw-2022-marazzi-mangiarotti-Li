package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.*;

public class Character3 implements CharacterBehavior{
    private int coin;
    private boolean firstUse;
    private int students[]= new int[5];

    public Character3(Game game) throws EriantysExceptions {

        Bag bag  = game.getTable().getBag();

        coin = 1;
        firstUse = false;
        students = bag.draw(4);
        this.students = students;

    }

    @Override
    public void useCharacter(Game game, Player user,Island island, int colore) throws EriantysExceptions {
        Bag bag  = game.getTable().getBag();
        island.putStudent(colore);
        int temp [] = bag.draw(1);

        for(int i = 0 ; i < students.length; i++){
            this.students[i] = this.students[i] + temp[i];
        }
        firstUse = true;
    }


}
