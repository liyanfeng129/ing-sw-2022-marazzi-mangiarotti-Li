package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private Table table;
    @BeforeEach
    public void setup(){
        table=new Table();
    }
    @Test
    public void test_mergeIsland(){
        table.getIslands(0).setTower(TowerColor.BLACK);
        table.getIslands(1).setTower(TowerColor.BLACK);
        table.getIslands(11).setTower(TowerColor.BLACK);
        try {
            table.mergeIsland();
        } catch (EriantysExceptions e) {
            fail();
        }
        assertEquals(10,table.getIslands().size());
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
}