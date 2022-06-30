package it.polimi.ingsw.model;


import java.io.Serializable;
import java.util.Arrays;

/**
 * this class implement the clouds of the game and their functionalities
 */
public class Cloud implements Serializable {
    private int size;
    private int[] students;

    /**
     * Constructor of Cloud
     * initialize the size at 0
     * initialize the students on the cloud a 0
     */
    public Cloud()
    {
        this.size = 0;
        this.students = new int[5];
    }

    /**
     * set the size of the cloud
     * @param size size of the cloud
     * @throws EriantysExceptions if the size is not allowed in the game
     */
    public  void setCloud(int size) throws EriantysExceptions
    {
        if(size==3 || size== 4)
            this.size = size;
        else
            throw new InnerExceptions.NotValidCloudSizeException("Cloud size is not valid");
    }

    /**
     * set number of student on the cloud a 0
     */
    public void emptyCloud()
    {
        for( int i = 0; i < students.length; i++)
            students[i] = 0;
    }

    public int getSize() {
        return size;
    }

    /**
     * get the students on the cloud
     * @return an array with the students on the cloud
     */
    public int[] getStudents() {
        return students;
    }


    /**
     * set the students on the cloud
     * @param students the student to be put on the cloud
     * @throws EriantysExceptions if number of students is different from the size of the cloud
     */
    public void setCloudStudents(int [] students) throws EriantysExceptions
    {
        if(Arrays.stream(students).sum() != getSize())
            throw new InnerExceptions.NotValidStudentSizeException("Number of student is not valid");
        this.students = students.clone();
    }

    @Override
    public Cloud clone() {
        try
        {
            Cloud temp = new Cloud();
            temp.setCloud(size);
            temp.setCloudStudents(students);
            return temp;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toString() {
        return "Cloud{" +
                "size=" + size +
                ", students=" + Arrays.toString(students) +
                '}';
    }
}
