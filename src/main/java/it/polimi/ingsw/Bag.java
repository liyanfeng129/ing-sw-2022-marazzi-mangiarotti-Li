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
        this.students = new int[] {26, 26, 26, 26, 26};
        this.N_students = 130;
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


    public void bagSet2() throws InnerExceptions.BagMax26 /** bagSet2 initializes the bag with 130 students in total, 26 for each kind*/
    {
        if (IntStream.of(this.students).sum() != 0)
            throw new InnerExceptions.BagMax26("Max 26 students for color");

        for(int i = 0 ; i < students.length; i++)
        {
            students[i] = 26;
            N_students = N_students+26;
        }
    }



    /** Extract randomly n students from bag
     *  @throws InnerExceptions.NotEnoughStudentsInBagException
     *  if there aren't n students in bag for extraction
     * */

    public int[] draw(int n) throws EriantysExceptions
    {
        int drawn_students[] = {0,0,0,0,0};
        if (IntStream.of(this.students).sum() < n)
            throw new InnerExceptions.NotEnoughStudentsInBagException("Not enough students in bag, is time to declare a winner!");


        for(int i = 0 ; i < n; i++)
        {
            if (IntStream.of(this.students).sum() == 0)
                throw new InnerExceptions.EmptyBag("Emptybag");

            if (IntStream.of(this.students).anyMatch(k-> k <0))
                throw new InnerExceptions.NegativeValue("NegativeValue");

            if (IntStream.of(this.students).sum() < n)
                throw new InnerExceptions.NotEnoughStudentsInBagException("Not enough students in bag, is time to declare a winner!");


            /** old draw algorithm

            Random rand = new Random();
            // Obtain a number between [0 - 4].
            int random = rand.nextInt(5);

            while (this.students[random] == 0)
                random = rand.nextInt(5);
            this.students[random] = this.students[random]-1;
            drawn_students[random] = drawn_students[random] +1;

             */

            /**
             * new draw algorithm with balanced probability
             * now the probability of extraction of one student is based on their quantity
             * in relationship with the total quantity
             */
            int range1Start = 0;
            int range2Start = this.students[0];
            int range3Start = range2Start + this.students[1];
            int range4Start = range3Start + this.students[2];
            int range5Start = range4Start + this.students[3];
            Random rand = new Random();
            // Obtain a number between [0 - 4].
            int random = rand.nextInt(IntStream.of(this.students).sum());
            if(random >= range1Start && random <= range1Start+this.students[0]-1)
            {
                this.students[0] --;
                drawn_students[0] ++;
            }
            if(random >= range2Start && random <= range2Start+this.students[1]-1)
            {
                this.students[1] --;
                drawn_students[1] ++;
            }
            if(random >= range3Start && random <= range3Start+this.students[2]-1)
            {
                this.students[2] --;
                drawn_students[2] ++;
            }
            if(random >= range4Start && random <= range4Start+this.students[3]-1)
            {
                this.students[3] --;
                drawn_students[3] ++;
            }
            if(random >= range5Start && random <= range5Start+this.students[4]-1)
            {
                this.students[4] --;
                drawn_students[4] ++;
            }

        }
        return drawn_students;
    }
}
