package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void test_player(){
        Player player=new Player();
        player.setName("Alessio");
        player.assignTower(TowerColor.BLACK);
        player.assignMage(Mage.MAGE1);
        assertEquals("Alessio",player.getName());
        assertEquals(Mage.MAGE1,player.getMage());
        assertEquals(TowerColor.BLACK,player.getTowerColor());
    }

}