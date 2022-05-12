package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

public class ActionPhase3 implements Phase {
    @Override
    public void next(Turn turn,Game game) {
        ArrayList<Object> response = new ArrayList();
        ArrayList<Object> msg_list = new ArrayList();
        //ricevo la nuvola che voglio caricare

        game.getTable().setCard9(-1);
        game.getTable().setCard6(false);//potrebbe esserci un problema se usato con altre carte
        game.getTable().setCard8(null);
        game.getTurnList().remove(0);
        if(game.getTurnList().get(0).getPlayerBoard().getN_tower()==0)//va messa in phase 2
            turn.setState(new EndGame());
        turn.setState(new StartTurn());
    }


    @Override
    public void printStatus() {

    }
}
