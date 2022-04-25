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
        int startingStudent[] = {26,26,26,26,26};
        int finalBag[] = {24, 24, 24, 24, 24};
        int islandStudent[] = {2, 2, 2, 2, 2};
        int temp1[] = new int[5];
        /*
        secondo me bagSet2 dopo le modifiche e inutile
        try {

            bag.bagSet2();
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(startingStudent, bag.getBag());
        */

        try {
            temp1 = bag.bagSet1();
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(finalBag, bag.getBag());
        assertArrayEquals(islandStudent, temp1);
    }
    @Test
    public void test_bag_Max26_Exceptions(){
        try {
            bag.setBag_test();
            bag.bagSet2();
        } catch (InnerExceptions.BagMax26 e) {
            e.printStackTrace();
        }
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
    //la draw secondo me ha un problema,non mi fa pescare piu di 65 di studenti
    @Test
    public void test_draw(){
        int temp[] = new int[5];
        try {
            temp=bag.draw(65);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertEquals(65, Arrays.stream(temp).sum());
        assertEquals(65, Arrays.stream(bag.getBag()).sum());
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