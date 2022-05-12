package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

public class PianificationState implements Phase{
    private int activePlayer=0;
    private List<Assistant> usedCards;
    private List<Player> playerQueue;
    @Override
    public void next(Turn turn, Game game) {
        ArrayList<Object> response = new ArrayList();
        ArrayList<Object> msg_list = new ArrayList();

        for(int i=0;i<game.getN_Player();i++) {
            //richiedi una carta da ogni giocatore in senso orario partendo da activeplayer

            Player player = (Player) msg_list.get(1);
            usedCards.add((Assistant) msg_list.get(2));
        }
        //calcola nuovo activeplayer e passa a nextstate
        setActivePlayer(game);

        turn.setState(new StartTurn());
    }

    @Override
    public void printStatus() {
        System.out.println("select an assistant from your hand");
    }


    public void setActivePlayer(Game game){
        int min=usedCards.get(0).getNum();
        int j=0;
        for (int i=0;i<usedCards.size();i++){
            if (usedCards.get(i).getNum()<min){
                min=min=usedCards.get(i).getNum();
                j=i;
            }
        }
        activePlayer=j;
        for (int i=0;i< game.getN_Player();i++){
            if((j+1)< game.getN_Player())
                playerQueue.add(game.getPlayers().get(j));
            else
                playerQueue.add(game.getPlayers().get(0));
            j++;
        }
        //game.setTurnList(playerQueue);
    }

}
