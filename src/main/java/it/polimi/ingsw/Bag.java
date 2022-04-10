package it.polimi.ingsw;

import  it.polimi.ingsw.Config;

import java.util.stream.Stream;

public class Bag {
    private final int[] students;
    private int N_students;
    Bag()
    {
        this.students = new int[5];
        this.N_students = 0;
    }

    public int[] bagSet1() throws EriantysExceptions /** bagSet1() takes out 2 students of each kind */
    {
        int temp[] = {2,2,2,2,2};

        for(int i = 0 ; i < students.length; i++) // I don't think this exception will ever happen
            if(students[i]-temp[i] < 0)            // let's keep it for now for a good practice
                throw new InnerExceptions.NotEnoughStudentsINBagException("Not enough students in bag");

        for(int i = 0 ; i < students.length; i++)
            students[i] = students[i]-temp[i];
        return temp;
    }

    public void bagSet2() /** bagSet2 initializes the bag with 120 students in total, 24 for each kind*/
    {
        for(int i = 0 ; i < students.length; i++)
        {
            students[i] = 24;
            N_students = N_students+24;
        }
    }
}
