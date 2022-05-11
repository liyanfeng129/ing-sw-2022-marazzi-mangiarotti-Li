package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Character5 implements CharacterBehavior{

    private int coin;
    private boolean firstUse;
    private String msg ;
    private int NoEntryTiles;


    public Character5(Game game) throws EriantysExceptions {

        msg = "";
        coin = 2;
        firstUse = false;
        NoEntryTiles=4;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore, Assistant assistant) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        int pos=game.getTable().getIslands().indexOf(island);
        game.getTable().getIslands(pos).setNoEntryTiles(true);
        NoEntryTiles--;
        //bisogna aggiungere che se ce questa carta non si calcola influenza e merge
    }
}

