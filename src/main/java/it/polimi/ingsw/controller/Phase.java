package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

public interface Phase {

    void next(Turn turn, Game game) throws EriantysExceptions;
    void printStatus();
}
