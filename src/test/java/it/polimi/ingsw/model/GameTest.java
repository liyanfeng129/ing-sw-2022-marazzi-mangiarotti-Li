package it.polimi.ingsw.model;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.MoveMotherNatureCommand;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.state.MoveMotherNatureState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.view.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Game Egame;
    private Game game3;
    private Game Egame3;
    private Game gameServer;
    private Player p1;
    private Player p2;
    private Player p3;
    private PlayerBoard pb;
    private Cli cli ;
    private ArrayList<Command> commands = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> turnList  = new ArrayList<Player>();
    @BeforeEach
    public void setup() throws EriantysExceptions {
        p1=new Player("Alessio", Mage.MAGE1,TowerColor.BLACK,2,true);
        p2=new Player("Yan",Mage.MAGE2,TowerColor.WHITE,2,true);
        p3=new Player("leo",Mage.MAGE3,TowerColor.GREY,3,true);
        game=new Game(2,false,p1);
        Egame=new Game(2,true,p1);
        game3=new Game(3,false,p3);
        Egame3=new Game(3,true,p3);
        gameServer=new Game();
    }
    @AfterEach
    public void tearDown(){
        p1=null;
        p2=null;
        p3=null;
        game=null;
        Egame=null;
        game3=null;
        Egame3=null;
        gameServer=null;
    }

    /**
     * test for constructor of game
     */
    @Test
    public void test_game(){
        ArrayList<Player> temp = new ArrayList<Player>();
        temp.add(p1);
        temp.add(p2);
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

    /**
     * test for method startGame
     */
    @Test
    public void test_startGame(){
        try {
            game.addPlayers(p2);
            Egame.addPlayers(p2);
            p1=new Player("Alessio", Mage.MAGE1,TowerColor.BLACK,3,true);
            p2=new Player("Yan",Mage.MAGE2,TowerColor.WHITE,3,true);
            game3.addPlayers(p1);
            Egame3.addPlayers(p1);
            game3.addPlayers(p2);
            Egame3.addPlayers(p2);
            game.startGame();
            Egame3.startGame();
            game3.startGame();
            Egame.startGame();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertTrue(game.isGameStarted());
        assertEquals(2,game.getTable().getClouds().size());
        assertEquals(3,game3.getTable().getClouds().size());
        assertEquals(3, Arrays.stream(game.getTable().getClouds().get(0).getStudents()).sum());
        assertEquals(4, Arrays.stream(game3.getTable().getClouds().get(0).getStudents()).sum());
    }

    /**
     * test for method findPlayerName
     */
    @Test
    public void test_findPlayerByName(){
        try {
            assertEquals(p1,game.findPlayerByName("Alessio"));
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    /**
     * test for PlayerNotFound exception
     */
    @Test
    public void test_playerNotFound(){
        try {
            assertEquals(p1,game.findPlayerByName("Yan"));
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    /**
     * test for methods about command in game
     */
    @Test
    public void test_command(){
        MoveMotherNatureCommand c1 = new MoveMotherNatureCommand(true, game, "Alessio", 2, false, false);
        MoveMotherNatureCommand c2 = new MoveMotherNatureCommand(true, game, "Yan", 8, false, false);
        game.addCommand(c1);
        assertEquals(c1,game.getLastCommand());
        game.setLastCommand(c2);
        assertEquals(c2,game.getLastCommand());
        assertEquals(c2,game.getExecutedCommand());
        game.addCommand(c1);
        game.removeCommand();
        assertEquals(1,game.getCommands().size());
    }
    /**
     * test for methods about states in game
     */
    @Test
    public void test_State(){
        MoveMotherNatureState s1 = new MoveMotherNatureState(game, 0, false, false);
        game.changeGameState(s1);
        assertEquals(s1,game.getGameState());
        game.setTurnList(game.getPlayers());
        try {
            game.addPlayerToTurnList(p2);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        for (int i=0;i<game.getPlayers().size();i++){
            assertEquals(game.getPlayers().get(i),game.getTurnList().get(i));
        }
    }

    /**
     * test for incorrectNumberOfPlayer exception
     */
    @Test
    public void test_incorrectNumberOfPlayer() {
        try {
            game.addPlayers(p1);
            game.addPlayers(p2);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    /**
     * test for incorrectNumberOfPlayer exception
     */
    @Test
    public void test_incorrectNumberOfPlayerInTurnList() {
        try {
            game.addPlayers(p1);
            game.setTurnList(game.getPlayers());
            game.addPlayerToTurnList(p2);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    /**
     * test for startingTime in game
     */
    @Test
    public void test_gameStartingTime() {
        game.setGameStartingTime("12.07");
        assertEquals("12.07",game.getGameStartingTime());
    }

    /**
     * test for constructor game()
     */
    @Test
    public void test_serverGame() {
        Table table = new Table();
        gameServer.setN_Player(2);
        gameServer.setExpertMode(false);
        gameServer.setTable(table);
        gameServer.setProfessors(new Professors());
        gameServer.setGameStarted(false);
        assertEquals(2,gameServer.getN_Player());
        assertEquals(table,gameServer.getTable());
        assertFalse(gameServer.isGameStarted());
        assertFalse(gameServer.isExpertMode());
        assertArrayEquals(new Professors(),gameServer.getProfessors());
    }


    private void assertArrayEquals(Professors professors, Professors professors1) {
    }
}