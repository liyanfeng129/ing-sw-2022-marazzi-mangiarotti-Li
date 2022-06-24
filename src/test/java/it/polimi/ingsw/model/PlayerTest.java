package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private  Player player1;
    private PlayerBoard pb;
    private Wallet wallet;
    @BeforeEach
    public void setup(){
         player=new Player("Alessio", Mage.MAGE1, TowerColor.BLACK,2,true);
         player1=new Player("Alessio");
         pb=new PlayerBoard(8,TowerColor.BLACK,7);
    }
    @Test
    public void test_player(){
        assertTrue(player.isCliClient());
        player1.setCliClient(true);
        assertTrue(player1.isCliClient());

        player.setName("Alessio");
        assertEquals("Alessio",player.getName());

        assertEquals(Mage.MAGE1,player.getMage());
        player1.assignMage(Mage.MAGE1);
        assertEquals(Mage.MAGE1,player1.getMage());

        assertEquals(TowerColor.BLACK,player.getTowerColor());
        player1.assignTower(TowerColor.BLACK);
        assertEquals(TowerColor.BLACK,player1.getTowerColor());

        player.setPb(pb);
        assertEquals(pb,player.getPb());

        assertEquals(1,player.getWallet().getSaving());
        player1.setWallet(new Wallet());
        assertEquals(1,player1.getWallet().getSaving());

        player.setUpdate(true);
        assertTrue(player.isUpdate());
    }

}