package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.*;

import java.io.Serializable;

public abstract class State implements Serializable {
   private Game game;
   private int phase;

    /**
     * constructor for State
     * @param game
     * @param phase int that indicate which player is currently playing
     */
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

    /**
     * compute if all action in this state have been done
     * @return true if game has to go to next state
     * @throws EriantysExceptions
     */
    public abstract boolean canChangeState() throws EriantysExceptions;

    /**
     * receive the choice made by player, compute  the event the controller have to make and call generateommand
     * @throws EriantysExceptions
     */
    public abstract void executeCommand() throws EriantysExceptions;

    /**
     * generate a new command
     * @return true if all went good
     * @throws EriantysExceptions
     */
    public abstract Command generateCommand() throws EriantysExceptions;

    /**
     * check if a card caused endGame
     * @return if game is finished or not
     */
    public boolean checkCardEndGame(){
        if (game.getTable().getIslands().size()<=3){
            return true;
        }
        for (int i=0;i< game.getN_Player();i++){
            if (game.getPlayers().get(i).getPb().getN_tower()<=0){
                return true;
            }
        }
        return false;
    }
}
