package it.polimi.ingsw.model;



import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Bag implements Serializable {
    private  int[] students;
    private int N_students;

    public Bag()
    {
        this.students = new int[] {26, 26, 26, 26, 26};
        this.N_students = 130;
    }

    public int[] getStudents() {
        return students;
    }

    public void setStudents(int[] students) {
        this.students = students;
    }

    public int getN_students() {
        return N_students;
    }

    public void setN_students(int n_students) {
        N_students = n_students;
    }

    public int[] getBag() {
        return students;
    }
    public void setBag_test(){
        students[0]=1;
    }
    public void setBag2_test(){
        students[0]=1;
        students[1]=1;
        students[2]=1;
        students[3]=-1;
        students[4]=1;

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




    /** Extract randomly n students from bag
     *  @throws InnerExceptions.NotEnoughStudentsInBagException
     *  if there aren't n students in bag for extraction
     * */

    public synchronized int[] draw(int n) throws EriantysExceptions
    {
        int drawn_students[] = {0,0,0,0,0};
        if (IntStream.of(this.students).sum() < n)
            throw new InnerExceptions.NotEnoughStudentsInBagException("Not enough students in bag, is time to declare a winner!");

        if (IntStream.of(this.students).sum() == 0)
            throw new InnerExceptions.EmptyBag("Emptybag");

        if (IntStream.of(this.students).anyMatch(k-> k <0))
            throw new InnerExceptions.NegativeValue("NegativeValue");
        for(int i = 0 ; i < n; i++)
        {

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

    @Override
    protected Bag clone() throws CloneNotSupportedException {
        Bag temp = new Bag();
        temp.setN_students(N_students);
        temp.setStudents(students.clone());
        return temp;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "students=" + Arrays.toString(students) +
                ", N_students=" + N_students +
                '}';
    }
}
