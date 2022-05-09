package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

public class StartGame implements Phase{
    @Override
    public void next(Turn turn,Game game) {
        turn.setState(new PianificationState());
    }

    @Override
    public void printStatus() {

    }
}
