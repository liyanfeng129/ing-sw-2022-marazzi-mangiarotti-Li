package it.polimi.ingsw.controller;

public class ActionPhase2 implements Phase {
    @Override
    public void next(Turn turn) {
        turn.setState(new ActionPhase3());
    }

    @Override
    public void prev(Turn turn) {

    }

    @Override
    public void printStatus() {

    }
    public ActionPhase2(){
        //sposta madrenatura
    }
}
