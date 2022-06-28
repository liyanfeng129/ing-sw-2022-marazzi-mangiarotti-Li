package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.InnerExceptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    private Bag bag;
    @BeforeEach
    public void setup(){
        bag=new Bag();
    }
    @AfterEach
    public void teardown(){
        bag=null;
    }

    /**
     * test for method which initialize students in bag
     */
    @Test
    public void test_bagSet(){
        assertEquals(130, Arrays.stream(bag.getBag()).sum());
        int finalBag[] = {24, 24, 24, 24, 24};
        int islandStudent[] = {2, 2, 2, 2, 2};
        int temp1[] = new int[5];
        assertEquals(130, Arrays.stream(bag.getBag()).sum());
        try {
            temp1 = bag.bagSet1();
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(finalBag, bag.getBag());
        assertArrayEquals(islandStudent, temp1);
    }

    /**
     * test if there are not enough students in bag
     */
    @Test
    public void test_bag_NegativeValue_Exceptions() {
        try {
            bag.setStudents(new int[]{0, 1, 0, 0, 0});
            bag.bagSet1();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    /**
     * test for method draw
     */
    @Test
    public void test_draw(){
        assertEquals(130, Arrays.stream(bag.getBag()).sum());
        int temp[] = new int[5];
        int temp1[]=new int[5];
        try {
            temp=bag.draw(60);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertEquals(60, Arrays.stream(temp).sum());
        assertEquals(70, Arrays.stream(bag.getBag()).sum());
        try {
            temp1=bag.draw(3);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertEquals(3, Arrays.stream(temp1).sum());
        assertEquals(67, Arrays.stream(bag.getBag()).sum());
    }

    /**
     * test if there are not enough students in bag
     */
    @Test
    public void test_draw_NotEnoughStudentInBag_exception(){
        int temp[] = new int[5];
            bag.setStudents(new int[]{0, 1, 0, 0, 0});
        try {
            temp= bag.draw(2);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }


}