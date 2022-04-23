package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

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

}