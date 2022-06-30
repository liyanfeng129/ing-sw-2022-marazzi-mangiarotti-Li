package it.polimi.ingsw.model;



import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * this class implement the bag of the game and its functionalities
 */
public class Bag implements Serializable {
    private  int[] students;
    private int N_students;

    /**
     * constructor of Bag
     * initialize the bag with 130 students,26 of each color
     */
    public Bag()
    {
        this.students = new int[] {6, 6, 6, 6, 6};
        this.N_students = 130;
    }

    public void setStudents(int[] students) {
        this.students = students;
    }

    public void setN_students(int n_students) {
        N_students = n_students;
    }

    /**
     * get all the students left in the bag
     * @return the students in the bag
     */
    public int[] getBag() {
        return students;
    }

    /**
     * bagSet1() takes out 2 students of each kind
     * @return 2 students of each color
     * @throws EriantysExceptions if there are not enought students left
     */
    public int[] bagSet1() throws EriantysExceptions
    {
        int temp[] = {2,2,2,2,2};

        for(int i = 0 ; i < students.length; i++) {
            students[i] = students[i] - temp[i];
            if (students[i] < 0)
                throw new InnerExceptions.NegativeValue("NegativeValue");
        }
        return temp;
    }

    /**
     * Extract randomly n students from bag
     * @param n number of students to be extracted
     * @return an array of students extracted from the bag
     * @throws EriantysExceptions  if there aren't n enought students in bag for extraction
     */
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

    /**
     * add one student to the bag
     * @param color the color of the student added in the bag
     */
    public void addStudentToBag(int color){
        students[color]++;
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
