package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    private Cloud cloud;
    @BeforeEach
    public void setup(){
        cloud=new Cloud();
    }
    @AfterEach
    public void teardown(){
        cloud=null;
    }
    @Test
    public void test_setCloud(){
        try {
            cloud.setCloud(3);
        } catch (EriantysExceptions e) {
            fail();
        }
        assertEquals(3,cloud.getSize());
    }
    @Test
    public void test_setCloud_NotValidSizeCloudException(){
        try {
            cloud.setCloud(5);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }
    //probabilmente e inutile visto che la get la testo dopo
    @Test
    public void test_getCloud(){
        int temp[]={0,0,0,0,0};
        try {
            cloud.setCloud(3);
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(temp, cloud.getStudents());
    }
    @Test
    public void test_student(){
        try {
            cloud.setCloud(3);
        } catch (EriantysExceptions e) {
            fail();
        }
        try {
            cloud.setStudents(new int[]{0, 1, 0, 1, 1});
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(new int[]{0, 1, 0, 1, 1}, cloud.getStudents());
        cloud.takeStudent(1);
        assertArrayEquals(new int[]{0, 0, 0, 1, 1}, cloud.getStudents());
    }
    @Test
    public void test_NotValidStudentSizeException(){
        try {
            cloud.setCloud(3);
            cloud.setStudents(new int[]{1, 2, 1, 0, 0});
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }

    }

}