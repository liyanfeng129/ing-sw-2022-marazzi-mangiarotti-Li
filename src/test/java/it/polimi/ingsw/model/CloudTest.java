package it.polimi.ingsw.model;

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

    /**
     * test NotValidSizeCloud exception
     */
    @Test
    public void test_setCloud_NotValidSizeCloudException(){
        try {
            cloud.setCloud(5);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    /**
     * test getter & setter for cloud & cloud students
     */
    @Test
    public void test_student(){
        try {
            cloud.setCloud(3);
        } catch (EriantysExceptions e) {
            fail();
        }
        try {
            cloud.setCloudStudents(new int[]{0, 1, 0, 1, 1});
        } catch (EriantysExceptions e) {
            fail();
        }
        assertArrayEquals(new int[]{0, 1, 0, 1, 1}, cloud.getStudents());

        cloud.emptyCloud();
        assertArrayEquals(new int[]{0, 0, 0, 0, 0}, cloud.getStudents());
    }

    /**
     * test for NotValidStudentSizeException
     */
    @Test
    public void test_NotValidStudentSizeException(){
        try {
            cloud.setCloud(3);
            cloud.setCloudStudents(new int[]{1, 2, 1, 0, 0});
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }

    }


}