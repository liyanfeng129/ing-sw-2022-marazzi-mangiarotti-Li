package it.polimi.ingsw.state;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndGameStateTest {
    private Game game;
    private Player p1;
    private Player p2;
    private Table table;
    private State state;

    @BeforeEach
    public void setup() throws EriantysExceptions {
        p1=new Player("Alessio", Mage.MAGE1,TowerColor.BLACK,2,false,true);
        p2=new Player("Yan",Mage.MAGE2,TowerColor.WHITE,2,false,true);
        game=new Game(2,true,p1);
    }
    @Test
    public void test_endgame(){
        game.setN_Player(2);
        state=new EndGameState(game,0);
        try {
            game.addPlayers(p2);
            table=new Table();
            game.setTable(table);
            game.startGame();
            Professors prof = new Professors();
            prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE, Mage.MAGE1});
            game.setProfessors(prof);
            game.getPlayers().get(0).getPlayerBoard().moveTower(-6);
            game.getPlayers().get(1).getPlayerBoard().moveTower(-6);
            //System.out.println(game.getPlayers().get(1));
            //game.getPlayers().get(0).getPlayerBoard().moveTower(-6);
            state.generateCommand();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test_endgame1(){
        game.setN_Player(2);
        state=new MoveMotherNatureState(game,0,false,false);
        try {
            new MoveMotherNatureState(game,0,false,false).setGameEnded(true);
            new MoveMotherNatureState(game,0,false,false).setCan(true);
            game.addPlayers(p2);
            table=new Table();
            game.setTable(table);
            game.startGame();
            Professors prof = new Professors();
            prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE, Mage.MAGE1});
            game.setProfessors(prof);
            game.getPlayers().get(0).getPlayerBoard().moveTower(-6);
            game.getPlayers().get(1).getPlayerBoard().moveTower(-6);
            //System.out.println(game.getPlayers().get(1));
            //game.getPlayers().get(0).getPlayerBoard().moveTower(-6);
            state.executeCommand();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }
}