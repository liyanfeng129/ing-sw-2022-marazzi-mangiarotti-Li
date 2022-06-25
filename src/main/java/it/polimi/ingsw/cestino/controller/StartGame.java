package it.polimi.ingsw.cestino.controller;

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
