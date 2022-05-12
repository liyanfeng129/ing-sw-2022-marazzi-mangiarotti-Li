package it.polimi.ingsw.characterCards;

import it.polimi.ingsw.model.*;

import java.util.Arrays;

public class Character3 implements CharacterBehavior{
    private int coin;
    private boolean firstUse;
    private String msg ;

    public Character3(Game game) throws EriantysExceptions {

        msg = "Choose an Island and resolve the Island as if\n" +
                "Mother Nature had ended her movement there. Mother\n" +
                "Nature will still move and the Island where she ends\n" +
                "her movement will also be resolved.";
        coin = 3;
        firstUse = false;

    }

    @Override
    public void useCharacter(Game game, Player user, Island island, int colore,Assistant assistant) throws EriantysExceptions {
        if (!this.firstUse){
            this.firstUse = true;
        }
        int MN_prec=game.getTable().getMotherNatureIndex();
        game.getTable().getIslands(MN_prec).setMotherNature(false);
        int MN_temp=game.getTable().getIslands().indexOf(island);
        game.getTable().getIslands(MN_temp).setMotherNature(true);
        for (int i = 0; i < game.getN_Player(); i++) {
            if (game.getTable().getPlayerMaxInfluence(game) == game.getPlayers().get(i))
                game.getTable().getIslands(game.getTable().getMotherNatureIndex()).setTower(game.getPlayers().get(i).getTowerColor());
        }
        game.getTable().mergeIsland();
        //dove si sposta madre natura in caso di merge?
        game.getTable().getIslands(MN_temp).setMotherNature(false);
        game.getTable().getIslands(MN_prec).setMotherNature(true);
    }

}
