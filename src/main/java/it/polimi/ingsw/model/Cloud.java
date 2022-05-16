package it.polimi.ingsw.model;


import java.io.Serializable;
import java.util.Arrays;

public class Cloud implements Serializable {
    private int size;
    private int[] students;

    public Cloud()
    {
        this.size = 0;
        this.students = new int[5];
    }

    public  void setCloud(int size) throws EriantysExceptions
    {
        if(size==3 || size== 4)
            this.size = size;
        else
            throw new InnerExceptions.NotValidCloudSizeException("Cloud size is not valid");
    }

    //non era meglio prendere tutto l array?
    public void setCloudStudent(int[] students)
    {
        this.students = students;
    }

    public void emptyCloud()
    {
        for( int i = 0; i < students.length; i++)
            students[i] = 0;
    }

    public int getSize() {
        return size;
    }
    public int[] getStudents() {
        return students;
    }

    public void setStudents(int [] students) throws EriantysExceptions
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
            temp.setStudents(students);
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
