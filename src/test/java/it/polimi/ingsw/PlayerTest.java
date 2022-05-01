package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private  Player player;
    private  Player Eplayer;
    private PlayerBoard pb;
    private Wallet wallet;
    @BeforeEach
    public void setup(){
        pb =new PlayerBoard(8, new int[]{0, 0, 0, 0, 0},7);
        player=new Player("Alessio",Mage.MAGE1,pb,TowerColor.BLACK);
        Eplayer=new Player("Alessio",Mage.MAGE1,pb,TowerColor.BLACK,wallet);
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