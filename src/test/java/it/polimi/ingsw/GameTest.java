package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Game Egame;
    private Player p1;
    private Player p2;
    private PlayerBoard pb;
    private Table table;
    @BeforeEach
    public void setup() throws EriantysExceptions {
        p1=new Player("Alessio", Mage.MAGE1,TowerColor.BLACK,2,false);
        p2=new Player("Yan",Mage.MAGE2,TowerColor.WHITE,2,false);
        game=new Game(2,false,p1);
        Egame=new Game(2,true,p1);
    }
    @Test
    public void test_game(){
        ArrayList<Player> temp = new ArrayList<Player>();
        temp.add(p1);
        temp.add(p2);
        game.setN_Player(2);
        try {
            game.addPlayers(p2);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertEquals(2,game.getN_Player());
        assertFalse(game.isExpertMode());
        assertTrue(Egame.isExpertMode());
        assertEquals(temp.get(0).getName(),game.getPlayers().get(0).getName());
        assertEquals(temp.get(1).getName(),game.getPlayers().get(1).getName());
    }
    @Test
    public void test_startGame2Player(){
        try {
            table=new Table();
            game.setTable(table);
            game.addPlayers(p2);
            game.startGame();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        // secondo me manca una get cloud in table
        // assertEquals(3,game.getTable().getcloud(0).getsize();

    }
    @Test
    public void test_startGame4Player(){
        try {
            table=new Table();
            game.setTable(table);
            game.setN_Player(4);
            game.addPlayers(p2);
            game.addPlayers(p2);
            game.addPlayers(p2);
            game.startGame();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        // secondo me manca una get cloud in table
        // assertEquals(3,game.getTable().getcloud(0).getsize();

    }
    @Test
    public void test_incorrectNumberOfPlayer(){
        try {
            table=new Table();
            game.setTable(table);
            game.addPlayers(p2);
            game.addPlayers(p2);
            game.startGame();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }
}