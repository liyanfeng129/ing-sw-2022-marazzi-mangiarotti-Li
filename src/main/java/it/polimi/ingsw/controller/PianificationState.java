package it.polimi.ingsw.controller;

public class PianificationState implements Phase{
    @Override
    public void next(Turn turn) {
        turn.setState(new ActionPhase1());
    }

    @Override
    public void prev(Turn turn) {

    }

    @Override
    public void printStatus() {
        System.out.println("select an assistant from your hand");
    }
    public PianificationState(){
        //richiedi una carta da ogni giocatore in senso orario partendo da activeplayer
        //calcola nuovo activeplayer e passa a nextstate
    }
}
