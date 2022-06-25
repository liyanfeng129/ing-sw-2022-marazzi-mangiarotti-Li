package it.polimi.ingsw.cestino.controller;

import it.polimi.ingsw.model.*;

import java.util.List;

public class Turn {
    private Game game;
    private int activePlayer=0;
    private List<Player> playerQueue = game.getPlayers();
    private Phase state = new PianificationState();
    private int endOfTurn = 1;
    private List<AssistantType> usedCard;

    public void setState(Phase state) {
        this.state = state;
    }

    public Phase getState() {
        return state;
    }

    public void nextState() throws EriantysExceptions {
        state.next(this,game);

    }

    public void printStatus() {
        state.printStatus();
    }

    public int getEndOfTurn() {
        return endOfTurn;
    }

    public void setEndOfTurn(int endOfTurn) {
        this.endOfTurn = endOfTurn;
    }

    public Turn(Game game) throws EriantysExceptions {
        this.game=game;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public List<Player> getPlayerQueue() {
        return playerQueue;
    }


    public List<AssistantType> playCard(AssistantType assistant) {
        usedCard.add(assistant);
        game.getPlayers().get(activePlayer).getHand().use_cards(assistant);
        return usedCard;
    }

    public void setActivePlayer() throws EriantysExceptions {
        if(usedCard.size()!= game.getN_Player())
            throw new InnerExceptions.InvalidCardNumberException("every player must play a card");
        int min=usedCard.get(0).getNum();
        int j=0;
        for (int i=0;i<usedCard.size();i++){
            if (usedCard.get(i).getNum()<min){
                min=min=usedCard.get(i).getNum();
                j=i;
            }
        }
        activePlayer=j;
    }

    public void nextPlayer(){
        if(getActivePlayer()+1 >= game.getN_Player())
            activePlayer=0;
        else
            activePlayer=activePlayer+1;
    }
}
