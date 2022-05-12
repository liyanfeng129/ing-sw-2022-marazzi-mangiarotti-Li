package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorsTest {
    private Game game;
    private Professors prof;
    private Player p1;
    private Player p2;
    private PlayerBoard pb1;
    private PlayerBoard pb2;
    @BeforeEach
    public void setup() throws EriantysExceptions {

        prof=new Professors();
       // p1=new Player("Alessio", Mage.MAGE1,TowerColor.BLACK,2,false);
        //p2=new Player("Leonardo",Mage.MAGE2,TowerColor.WHITE,2,false);
        try {
            p1.getPlayerBoard().setDiningRoom(new int[]{1, 3, 2, 1, 2});
        } catch (EriantysExceptions e) {
            fail();
        }
        try {
            p2.getPlayerBoard().setDiningRoom(new int[]{0, 3, 3, 2, 2});
        } catch (EriantysExceptions e) {
            fail();
        }
        game=new Game(2,false,p1);
        game.addPlayers(p2);
    }
    @Test
    public void test_assignProfessor(){
        prof.assignProfessor(game.getPlayers());
        assertArrayEquals(new Mage[]{Mage.MAGE1, Mage.NO_MAGE, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE}, prof.getList_professors());
    }

}