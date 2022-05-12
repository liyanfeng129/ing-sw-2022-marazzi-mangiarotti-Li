package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private  Player Eplayer;
    private PlayerBoard pb;
    private Wallet wallet;
    @BeforeEach
    public void setup(){
       // player=new Player("Alessio", Mage.MAGE1, TowerColor.BLACK,2,false);
        Eplayer=new Player("Alessio",Mage.MAGE1,TowerColor.BLACK,wallet,2,false);
    }
    @Test
    public void test_player(){
        player.setName("Alessio");
        player.assignTower(TowerColor.BLACK);
        player.assignMage(Mage.MAGE1);
        assertEquals("Alessio",player.getName());
        assertEquals(Mage.MAGE1,player.getMage());
        assertEquals(TowerColor.BLACK,player.getTowerColor());
    }

}