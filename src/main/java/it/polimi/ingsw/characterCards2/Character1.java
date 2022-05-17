package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public class Character1 extends CharacterCard implements Serializable {
    private boolean dataGathered;
    private int students[] = new int[5];
    int student_color;
    int island_pos;
    public Character1(int [] students) {
        setFirstUse(true);
        setCoin(1);
        String msg = "Take 1 Student from this card and place it on\n" +
                "an Island of your choice. Then, draw a new Student\n" +
                "from the Bag and place it on this card.";
        setMsg(msg);
        this.students = students;
    }
    /*
    *  if (!this.firstUse){
            this.firstUse = true;
        }
        Bag bag  = game.getTable().getBag();
        island.putStudent(colore);
        int temp [] = bag.draw(1);

        for(int i = 0 ; i < students.length; i++){
            this.students[i] = this.students[i] + temp[i];
        }
    * */

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(isFirstUse())
            setFirstUse(false);
        return false;
    }

    @Override
    public boolean undoEffect(Game game, Player player) throws EriantysExceptions {
        return false;
    }

    @Override
    public boolean getData() throws EriantysExceptions {
        
        return false;
    }
}
