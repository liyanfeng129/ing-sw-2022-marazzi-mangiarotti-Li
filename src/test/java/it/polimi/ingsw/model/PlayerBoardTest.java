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
    /*
    secondo me non serve
    @Test
    public void test_pbProfessor(){
        prof=new Professors();
        prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.NO_MAGE, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE});

    }
    */

}