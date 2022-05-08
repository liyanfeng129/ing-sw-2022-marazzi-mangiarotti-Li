package it.polimi.ingsw.controller;

public class ActionPhase3 implements Phase {
    @Override
    public void next(Turn turn) {
        //if endOfTurn != getN_Players
        turn.setState(new ActionPhase1());
        //else
        turn.setState(new PianificationState());
    }

    @Override
    public void prev(Turn turn) {

    }

    @Override
    public void printStatus() {

    }
    public ActionPhase3(){
        //seleziona nuvola,cambia active Player
    }
}
