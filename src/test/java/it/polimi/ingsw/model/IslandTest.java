package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    private Island island;
    @BeforeEach
    public void setup(){
        island=new Island();
    }
    @AfterEach
    public void teardown(){
        island=null;
    }

    /**
     * test of method about students
     */
    @Test
    public void test_student(){
        int temp[] = new int[5];
        island.addStudent(0);
        island.addStudent(0);
        island.addStudent(3);
        assertEquals(2,island.getStudents()[0]);
        assertArrayEquals(new int[]{2, 0, 0, 1, 0}, island.getStudents());
        island.mergeStudents(new int[]{0,1,0,1,0});
        assertArrayEquals(new int[]{2,1,0,2,0}, island.getStudents());
    }

    /**
     * test for getter & setter of MotherNature
     */
    @Test
    public void test_motherNature(){
        island.setMotherNature(true);
        assertTrue(island.getMotherNature());
    }

    /**
     * test for getter & setter of tower
     */
    @Test
    public void test_tower(){
        island.setTowerColor(TowerColor.BLACK);
        assertEquals(TowerColor.BLACK,island.getTowerColor());
    }

    /**
     * test for method increasingSize
     */
    @Test
    public void test_size(){
        island.IncreasingSize(1);
        assertEquals(2,island.getSize());
    }

    /**
     * test for getter & setter of noEntryTile
     */
    @Test
    public void test_noEntryTile(){
        island.setNoEntryTiles(true);
        assertTrue(island.isNoEntryTiles());
        island.setNoEntryTiles(false);
        assertFalse(island.isNoEntryTiles());
    }
}