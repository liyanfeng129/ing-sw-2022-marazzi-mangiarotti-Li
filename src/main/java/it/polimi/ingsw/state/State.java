package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.*;

import java.io.Serializable;

public abstract class State implements Serializable {
   private Game game;
   private int phase;

   public State(Game game,int phase)
   {
       this.game = game;
       this.phase = phase;
   }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public abstract void nextState() throws EriantysExceptions;
    public abstract boolean canChangeState() throws EriantysExceptions;
    public abstract void executeCommand() throws EriantysExceptions;
    public abstract Command generateCommand() throws EriantysExceptions;

}
