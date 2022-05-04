package it.polimi.ingsw;

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
    @Test
    public void test_bag_NegativeValue_Exceptions() {
        try {
            bag.setBag_test();
            bag.bagSet1();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }
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
        int temp2[]= bag.getBag();
        System.out.println(temp2);
        try {
            temp1=bag.draw(35);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        //assertEquals(64, Arrays.stream(temp1).sum());
        assertEquals(35, Arrays.stream(bag.getBag()).sum());
    }

    @Test
    public void test_draw_EmptyBag_exception(){
        int temp[] = new int[5];
            bag.setBag_test();
        try {
            temp= bag.draw(2);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }

    }
    @Test
    public void test_draw_NegativeValue_exception(){
        int temp[] = new int[5];
        bag.setBag2_test();
        try {
            temp= bag.draw(3);
        } catch (InnerExceptions.EmptyBag e) {
            e.printStackTrace();
        } catch (InnerExceptions.NegativeValue e) {
            e.printStackTrace();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }

    }

}