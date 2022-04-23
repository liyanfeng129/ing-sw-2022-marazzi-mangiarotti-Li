package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Test
    public void test_mergeIsland(){
        Table table=new Table();
        table.getIslands(0).setTower("black");
        table.getIslands(1).setTower("black");
        table.getIslands(11).setTower("black");
        try {
            table.mergeIsland();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertEquals(10,table.getIslands().size());
    }
    @Test
    public void test_moveMn(){
        Table table=new Table();
        try {
            table.moveMotherNature(3);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertTrue(table.getIslands(3).getMotherNature());
        assertFalse(table.getIslands(0).getMotherNature());
    }

}