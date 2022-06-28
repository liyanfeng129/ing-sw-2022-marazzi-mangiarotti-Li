package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {
    private PlayerBoard pb;
    private Professors prof;
    @BeforeEach
    public void setup(){
        pb=new PlayerBoard(8, TowerColor.BLACK,7);
    }
    @AfterEach
    public void tearDown(){
            pb=null;
    }
    /**
     * test for getter & setter of student in playerBoard
     */
    @Test
    public void test_pbStudent(){
        try {
            pb.setDiningRoom(new int[]{0, 3, 2, 1, 2});
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(new int[]{0, 3, 2, 1, 2},pb.getDiningRoom());
        try {
            pb.setWaitingRoom(new int[]{0, 1, 2, 1, 2});
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(new int[]{0, 1, 2, 1, 2},pb.getWaitingRoom());
        try {
            pb.addStudentToDiningRoom(1);
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(new int[]{0, 4, 2, 1, 2},pb.getDiningRoom());
        try {
            pb.takeStudentFromWaitingRoom(2);
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(new int[]{0, 1, 1, 1, 2},pb.getWaitingRoom());
        pb.setMaxStudentsInWaiting(7);
        assertEquals(7,pb.getMaxStudentsInWaiting());
    }

    /**
     * test for invalid number of student
     */
    @Test
    public void test_pbNotValidStudentException(){
        try {
            pb.setDiningRoom(new int[]{0, 11, 2, -1, 2});
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        try {
            pb.setWaitingRoom(new int[]{0, 1, 2, 4, 2});
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        try {
            pb.setDiningRoom(new int[]{0, 3, 10, 1, 2});
        } catch (EriantysExceptions e) {
            fail();
        }
        try {
            pb.addStudentToDiningRoom(2);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        try {
            pb.setWaitingRoom(new int[]{0, 1, 2, 1, 2});
        } catch (EriantysExceptions e) {
            fail();
        }
        try {
            pb.takeStudentFromWaitingRoom(0);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    /**
     * test for getter & setter of attribute tower
     */
    @Test
    public void test_pbTower(){
        try {
            pb.setN_tower(8);
        } catch (EriantysExceptions e) {
            fail();
        }
        assertEquals(8,pb.getN_tower());
            pb.moveTower(-2);
        assertEquals(6,pb.getN_tower());
    }

    /**
     * test for exception InvalidTowerNumberException
     */
    @Test
    public void test_InvalidTowerNumberException(){
        try {
            pb.setN_tower(9);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
        try {
            pb.setN_tower(8);
        } catch (EriantysExceptions e) {
            fail();
        }
            pb.moveTower(+1);
    }

    /**
     * test for method addCloudToWaiting
     */
    @Test
    public void test_addCloudToWaiting() {
        pb.addCloudToWaitingRoom(new int[]{0, 1, 2, 0, 0});
        assertArrayEquals(new int[]{0, 1, 2, 0, 0},pb.getWaitingRoom());
    }

    /**
     * test for methods in playerBoard called by character cards
     * @throws EriantysExceptions if not enough students in playerboard
     */
    @Test
    public void test_CharacterCard() throws EriantysExceptions {
        pb.addStudentsToDiningRoom(new int[]{0, 1, 2, 0, 0});
        try {
            pb.takeStudentFromDiningRoom(2);
        } catch (InnerExceptions.NegativeValue e) {
            e.printStackTrace();
        }
        assertArrayEquals(new int[]{0, 1, 1, 0, 0},pb.getDiningRoom());
        try {
            pb.takeStudentFromDiningRoom(0);
        } catch (InnerExceptions.NegativeValue e) {
            e.printStackTrace();
        }
        pb.removeStudentFromDiningRoom(new int[]{0, 1, 1, 0, 0});
        assertArrayEquals(new int[]{0, 0, 0, 0, 0},pb.getDiningRoom());
        pb.addStudentsToWaitingRoom(new int[]{2, 0, 0, 1, 0});
        assertArrayEquals(new int[]{2, 0, 0, 1, 0},pb.getWaitingRoom());
        pb.removeStudentFromWaitingRoom(new int[]{2, 0, 0, 1, 0});
        assertArrayEquals(new int[]{0, 0, 0, 0, 0},pb.getWaitingRoom());
        pb.setCoin3(0);
        assertArrayEquals(new boolean[]{true, false, false, false, false},pb.getCoin3());
        pb.setCoin6(2);
        assertTrue(pb.getCoin6()[2]);
        pb.setCoin9(4);
        assertTrue(pb.getCoin9()[4]);

    }

}