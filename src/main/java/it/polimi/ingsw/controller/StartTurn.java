package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

public class StartTurn implements Phase{
    @Override
    public void next(Turn turn, Game game) throws EriantysExceptions {
        if(game.getTurnList().size()!=0)
                turn.setState(new ActionPhase1());
        else {
            int ClouSize = game.getTable().getClouds().get(0).getSize();
            for (int i = 0; i < game.getTable().getClouds().size(); i++) {

                game.getTable().getClouds().get(i).setCloudStudent(game.getTable().getBag().draw(ClouSize));
            }
            turn.setState(new PianificationState());
        }
    }

    @Override
    public void printStatus() {

    }
}
