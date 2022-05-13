package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character12 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private String msg ;


    public Character12(Game game) throws EriantysExceptions {

        msg = "";
        coin = 3;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, int[] students) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        for (int i=0;i< game.getN_Player();i++){
            int DiningRoomStudents=game.getPlayers().get(i).getPlayerBoard().getDiningRoom()[colore];
            for (int j=0;j<3 && DiningRoomStudents>0;j++){
                game.getPlayers().get(i).getPlayerBoard().takeStudentFromWaitingRoom(colore);
            }
        }
    }
}
