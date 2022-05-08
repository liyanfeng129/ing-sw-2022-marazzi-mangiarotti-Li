package it.polimi.ingsw.controller;

public interface Phase {

    void next(Turn turn);
    void prev(Turn turn);
    void printStatus();
}
