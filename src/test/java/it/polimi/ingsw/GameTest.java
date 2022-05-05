package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    @BeforeEach
    public void setup(){
        //game=new Game();
    }
    @Test
    public void test_game(){
        ArrayList<Player> temp = new ArrayList<Player>();
       // Player p1=new Player("alessio");
       // Player p2=new Player("leonardo");
        //temp.add(p1);
        //temp.add(p2);
        game.setN_Player(2);
      //  game.setGamemode(true);
        //game.addPlayers("alessio");
        //game.addPlayers("leonardo");
        assertEquals(2,game.getN_Player());
        //assertTrue(game.getGamemode());
        assertEquals(temp.get(0).getName(),game.getPlayers().get(0).getName());
        assertEquals(temp.get(1).getName(),game.getPlayers().get(1).getName());
    }

}