package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    private Island island;
    @BeforeEach
    public void setup(){
        island=new Island();
    }
    @Test
    public void test_student(){
        int temp[] = new int[5];
        island.addStudent(0);
        island.addStudent(0);
        island.addStudent(3);
        temp=island.getStudents();
        assertEquals(2,temp[0]);
        assertArrayEquals(island.getStudents(), temp);
        island.mergeStudents(temp);
        assertArrayEquals(temp, island.getStudents());
    }
    @Test
    public void test_motherNature(){
        island.setMotherNature(true);
        assertTrue(island.getMotherNature());
    }
    @Test
    public void test_tower(){
        island.setTower(TowerColor.BLACK);
        assertEquals(TowerColor.BLACK,island.getTower());
    }
    @Test
    public void test_size(){
        island.IncreasingSize(1);
        assertEquals(2,island.getSize());
    }
}