package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.TowerColor;

import java.util.ArrayList;

public class ActionPhase2 implements Phase {
    @Override
    public void next(Turn turn, Game game) throws EriantysExceptions {
        ArrayList<Object> response = new ArrayList();
        ArrayList<Object> msg_list = new ArrayList();
        //ricevo dove spostare MN
        game.getTable().moveMotherNature((Integer) msg_list.get(2));
        //game.getTable().getInfluence(game,game.getProfessors());
        //settower del colore di chi ha influenza piu alta
        int MN_pos = game.getTable().getMotherNatureIndex();
        TowerColor color=game.getTable().getPlayerMaxInfluence(game).getTowerColor();
        game.getTable().getIsland(MN_pos).setTower(color);
        game.getTable().mergeIsland();
        if(game.getTable().getIslands().size() <=3)
            turn.setState(new EndGame());
        turn.setState(new ActionPhase3());
    }

    @Override
    public void printStatus() {

    }
}
