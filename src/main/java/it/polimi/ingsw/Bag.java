package it.polimi.ingsw;

import  it.polimi.ingsw.Config;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Bag {
    private final int[] students;
    private int N_students;

    public Bag()
    {
        this.students = new int[5];
        this.N_students = 0;
    }

    public int[] getBag() {
        return students;
    }
    public void setBag_test(){
        students[0]=1;
    }
    public void setBag2_test(){
        students[0]=1;
        students[0]=1;
        students[0]=1;
        students[0]=-1;
        students[0]=1;

    }
    public int[] bagSet1() throws EriantysExceptions /** bagSet1() takes out 2 students of each kind */
    {
        int temp[] = {2,2,2,2,2};

        for(int i = 0 ; i < students.length; i++) {
            students[i] = students[i] - temp[i];
            if (students[i] < 0)
                throw new InnerExceptions.NegativeValue("NegativeValue");
        }
        return temp;
    }


    public void bagSet2() throws InnerExceptions.BagMax26 /** bagSet2 initializes the bag with 120 students in total, 24 for each kind*/
    {
        if (IntStream.of(this.students).sum() != 0)
            throw new InnerExceptions.BagMax26("Max 26 students for color");

        for(int i = 0 ; i < students.length; i++)
        {
            students[i] = 26;
            N_students = N_students+26;
        }
    }


    public int[] draw(int n) throws InnerExceptions.EmptyBag, InnerExceptions.NegativeValue /** draw the number of student in a random order*/
    {
        int drawn_students[] = {0,0,0,0,0};


        for(int i = 0 ; i < n; i++)
        {
            if (IntStream.of(this.students).sum() == 0)
                throw new InnerExceptions.EmptyBag("Emptybag");

            if (IntStream.of(this.students).anyMatch(k-> k <0))
                throw new InnerExceptions.NegativeValue("NegativeValue");

            Random rand = new Random();
            // Obtain a number between [0 - 4].
            int random = rand.nextInt(5);

            while (this.students[random] == 0)
                random = rand.nextInt(5);
            this.students[random] = this.students[random]-1;
            drawn_students[random] = drawn_students[random] +1;
        }
        return drawn_students;
    }
}
