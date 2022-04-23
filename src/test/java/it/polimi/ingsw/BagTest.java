package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    @Test
    public void test_bagSet2(){
        Bag b=new Bag();
        int startingStudent[] = {26,26,26,26,26};
        int finalBag[] = {24, 24, 24, 24, 24};
        int islandStudent[] = {2, 2, 2, 2, 2};
        int temp1[] = new int[5];
        try {
            b.bagSet2();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertArrayEquals(startingStudent, b.getBag());
        try {
            temp1 = b.bagSet1();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        assertArrayEquals(finalBag, b.getBag());
        assertArrayEquals(islandStudent, temp1);
    }
    @Test
    public void test_draw(){
        Bag b=new Bag();
        int temp[] = new int[5];
        try {
            b.bagSet2();
        } catch (InnerExceptions.BagMax26 e) {
            e.printStackTrace();
        }
        try {
            temp=b.draw(124);
        } catch (InnerExceptions.EmptyBag e) {
            e.printStackTrace();
        } catch (InnerExceptions.NegativeValue e) {
            e.printStackTrace();
        }
        assertEquals(124,Arrays.stream(temp).sum());
        //assertEquals(123,Arrays.stream(b).sum());
    }

}