package it.polimi.ingsw;


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
    public void takeStudent(int stu)
    {
        this.students[stu]--;
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
        this.students = students;
    }
}
