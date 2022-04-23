package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void test_game(){
        Game game=new Game();
        ArrayList<Player> temp = new ArrayList<Player>();
        Player p1=new Player("alessio");
        Player p2=new Player("leonardo");
        temp.add(p1);
        temp.add(p2);
        game.setN_Player(2);
        game.setGamemode(true);
        game.addPlayers("alessio");
        game.addPlayers("leonardo");
        assertEquals(2,game.getN_Player());
        assertTrue(game.getGamemode());
       // assertEquals(temp,game.getPlayers());
    }

}