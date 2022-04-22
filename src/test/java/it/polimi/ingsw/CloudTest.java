package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    @Test
    public void test_setCloud(){
        Cloud cloud=new Cloud();
        try {
            cloud.setCloud(3);
        } catch (EriantysExceptions e) {
            System.out.println(e);
            fail();
        }
        assertEquals(3,cloud.getSize());
    }
    /*
    non so come fare questo test,a cosa dve essere uguale la cloud?
    @Test
    public void test_getCloud(){
        Cloud cloud=new Cloud();
        assertArrayEquals(, cloud.getStudents());
    }
     */

}