package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private Table table;
    @BeforeEach
    public void setup(){
        try {
            table=new Table();
        } catch (EriantysExceptions e) {
            fail();
        }
    }
    @Test
    public void test_mergeIsland(){
        int temp[]=table.getIslands(0).getStudents();
        table.getIslands(0).setTower(TowerColor.BLACK);
        table.getIslands(1).setTower(TowerColor.BLACK);
        table.getIslands(11).setTower(TowerColor.BLACK);
        table.getIslands(0).addStudent(3);
        temp[3]++;
        table.getIslands(11).addStudent(0);
        temp[0]++;
        try {
            table.mergeIsland();
        } catch (EriantysExceptions e) {
            fail();
        }
        assertEquals(10,table.getIslands().size());
        assertArrayEquals(temp,table.getIslands(0).getStudents());

    }
    @Test
    public void test_moveMn(){
        try {
            table.moveMotherNature(3);
        } catch (EriantysExceptions e) {
            fail();
        }
        assertTrue(table.getIslands(3).getMotherNature());
        assertFalse(table.getIslands(0).getMotherNature());
    }

    @Test
    public void test_getMN_index_exception(){
        table.getIslands(0).setMotherNature(false);
        try {
            table.moveMotherNature(3);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test_influence() throws EriantysExceptions {
        Player p1=new Player("ale",Mage.MAGE1,TowerColor.WHITE,2,false);
        Player p2=new Player("leo",Mage.MAGE2,TowerColor.BLACK,2,false);
        Game game = new Game(2,false,p1);
        game.addPlayers(p2);
        game.getTable().getIslands(0).setMotherNature(true);
        Professors prof=new Professors();
        prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.NO_MAGE, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE});
        game.getTable().getIslands(0).setTower(TowerColor.WHITE);
        game.getTable().getIslands(0).addStudent(0);
        game.getTable().getIslands(0).addStudent(0);
        game.getTable().getIslands(0).addStudent(1);
        game.getTable().getIslands(0).addStudent(2);
        assertArrayEquals(new int[]{3, 1,0,0}, table.getInfluence(game,prof));

    }
}