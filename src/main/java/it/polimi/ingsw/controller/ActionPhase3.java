package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

public class ActionPhase3 implements Phase {
    @Override
    public void next(Turn turn,Game game) {
        ArrayList<Object> response = new ArrayList();
        ArrayList<Object> msg_list = new ArrayList();
        //ricevo la nuvola che voglio caricare

        //if endOfTurn != getN_Players
        turn.setState(new ActionPhase1());
        //else
        turn.setState(new PianificationState());
    }


    @Override
    public void printStatus() {

    }
}
