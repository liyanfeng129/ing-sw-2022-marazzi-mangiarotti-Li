package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    @Test
    public void test_student(){
        Island island=new Island();
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
        Island island=new Island();
        island.setMotherNature(true);
        assertTrue(island.getMotherNature());
    }
    @Test
    public void test_tower(){
        Island island=new Island();
        island.setTower("black");
        assertEquals("black",island.getTower());
    }
    @Test
    public void test_size(){
        Island island=new Island();
        island.IncreasingSize(1);
        assertEquals(2,island.getSize());
    }
}