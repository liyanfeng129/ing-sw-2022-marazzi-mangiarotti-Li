package it.polimi.ingsw.model;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Professors;
import it.polimi.ingsw.model.TowerColor;
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
            pb.addStudentToHolder(1);
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
            pb.addStudentToHolder(2);
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
    @Test
    public void test_addCloudToWaiting() throws EriantysExceptions {
        pb.addCloudToWaitingRoom(new int[]{0, 1, 2, 0, 0});
        assertArrayEquals(new int[]{0, 1, 2, 0, 0},pb.getWaitingRoom());
    }
    @Test
    public void test_CharacterCard() {
        pb.addStudentsToDiningRoom(new int[]{0, 1, 2, 0, 0});
        try {
            pb.takeStudentFromHolder(2);
        } catch (InnerExceptions.NegativeValue e) {
            e.printStackTrace();
        }
        assertArrayEquals(new int[]{0, 1, 1, 0, 0},pb.getDiningRoom());
        try {
            pb.takeStudentFromHolder(0);
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
    /*
    secondo me non serve
    @Test
    public void test_pbProfessor(){
        prof=new Professors();
        prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.NO_MAGE, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE});

    }
    */

}