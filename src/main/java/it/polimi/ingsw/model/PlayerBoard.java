package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Arrays;

public class PlayerBoard implements Serializable {
    private int[] diningRoom;
    private int[] waitingRoom;
    private TowerColor tower;
    private int n_tower;
    private Boolean[] profHolder;
    private int maxStudentsInWaiting;
    private boolean[] coin3;
    private boolean[] coin6;
    private boolean[] coin9;


    /**
     * constructor of class PlayerBoard
     * @param n_tower initialize the number of towers
     * @param tower_color initialize color of towers
     * @param maxStudentsInWaiting initialize max number in waiting room
     */
    public PlayerBoard(int n_tower,TowerColor tower_color, int maxStudentsInWaiting) {
        this.diningRoom = new int[5];
        this.waitingRoom = new int[5];
        for(int i = 0; i < 5; i++)
        {
            this.diningRoom[i] = 0;
            this.waitingRoom[i] = 0;
        }
        this.tower = tower_color;
        this.n_tower = n_tower ;
        this.maxStudentsInWaiting = maxStudentsInWaiting;
        this.coin3 = new boolean[]{false, false, false, false, false};
        this.coin6 = new boolean[]{false, false, false, false, false};
        this.coin9 = new boolean[]{false, false, false, false, false};

    }

    public int[] getDiningRoom() {
        return diningRoom;
    }

    /**
     * set students in dining room
     * @param diningRoom array of student to be setted
     * @throws EriantysExceptions if number of student  in @param is not valid
     */
    public void setDiningRoom(int[] diningRoom) throws EriantysExceptions {
        for (int i=0;i<5;i++){
            if(diningRoom[i]>10 || diningRoom[i]<0)
                throw new InnerExceptions.NotValidStudentSizeException("Number of student is not valid");
        }
        this.diningRoom = diningRoom;
    }

    public int[] getWaitingRoom() {
        return waitingRoom;
    }

    /**
     * set students in waiting room
     * @param waitingRoom array of student to be setted
     * @throws EriantysExceptions if number of student  in @param is not valid
     */
    public void setWaitingRoom(int[] waitingRoom) throws EriantysExceptions {
        if(Arrays.stream(waitingRoom).sum() > maxStudentsInWaiting || Arrays.stream(waitingRoom).sum() <0)
            throw new InnerExceptions.NotValidStudentSizeException("Number of student is not valid");
        this.waitingRoom = waitingRoom.clone();
    }

    /**
     * add an array of student to waiting room
     * @param cloud array to be added
     */
    public void addCloudToWaitingRoom(int[] cloud){
        waitingRoom = sumArray(waitingRoom, cloud);
    }

    public int getN_tower() {
        return n_tower;
    }
//secondo me si puo eliminare sto metodo
    /**
     * set number of tower
     * @param n_tower
     * @throws EriantysExceptions if @param is different from number of tower initialized in costructor
     */
    public void setN_tower(int n_tower) throws EriantysExceptions {
        if(this.n_tower != n_tower)
            throw new InnerExceptions.InvalidTowerNumberException("Invalid tower number exception!");

        this.n_tower = n_tower;
    }


    //secondo me non servono, ho un array a parte che mi dice chi ha i professori
    /*
    public Boolean[] getProfHolder() {
        return profHolder;
    }
    public void setProfHolder(Boolean[] profHolder) {
        this.profHolder = profHolder;
    }
    public void setProf(int prof, boolean occupy)
    {
        this.profHolder[prof] = occupy;
    }*/

    /**
     * add or remove tower from playerBoard
     * @param n number of tower to be added or removed
     */
    public void moveTower(int n)
    {
        n_tower += n;
    }


    /**
     * sum two arrays
     * @param array1 first array
     * @param array2 second array
     * @return
     */
    private int[] sumArray(int[] array1, int[] array2){
        for(int i=0;i<array1.length;i++){
            array1[i]=array1[i]+array2[i];
        }
        return array1;
    }

    /**
     * remove a student from waiting room
     * @param student color of the student to be removed
     * @throws EriantysExceptions if there are 0 students of this color
     */
    public void takeStudentFromWaitingRoom(int student) throws EriantysExceptions
    {
        if((this.waitingRoom[student]-1)<0)
            throw new InnerExceptions.NegativeValue("Negative Value");
        this.waitingRoom[student] -- ;
    }

    /**
     * add a student to waiting room
     * @param student color of the student to be added
     * @throws EriantysExceptions if max number of student in waiting is already reached
     */
    public  void addStudentToDiningRoom(int student) throws EriantysExceptions
    {
        if((this.diningRoom[student]+1)>10)
            throw new InnerExceptions.NotValidStudentSizeException("Number of student is already at max");
        this.diningRoom[student] ++;
    }

    /**
     * remove a student from dining room
     * @param student color of the student to be removed
     * @throws InnerExceptions.NegativeValue if there are 0 students of this color
     */
    public  void takeStudentFromDiningRoom(int student) throws InnerExceptions.NegativeValue {
        if(this.diningRoom[student]==0)
            throw new InnerExceptions.NegativeValue("Not Enought student");
        this.diningRoom[student] --;
    }

    public int getMaxStudentsInWaiting() {
        return maxStudentsInWaiting;
    }

    /**
     * remove an array of students from waiting room
     * @param students array of student to be removed
     * @throws EriantysExceptions if there are not enougth student to be removed
     */
    public void removeStudentFromWaitingRoom(int students[]) throws EriantysExceptions{
        for(int i = 0; i < 5; i++){
            if(this.waitingRoom[i]==0)
                throw new InnerExceptions.NegativeValue("Not Enought student");
            this.waitingRoom[i] -= students[i];
            }
    }

    /**
     * remove an array of students from dining room
     * @param students array of student to be removed
     * @throws EriantysExceptions if there are not enougth student to be removed
     */
    public void removeStudentFromDiningRoom(int students[]) throws EriantysExceptions{
        for(int i = 0; i < 5; i++) {
            if (this.diningRoom[i] == 0)
                throw new InnerExceptions.NegativeValue("Not Enought student");
            this.diningRoom[i] -= students[i];
        }
    }

    /**
     * add an array of students to dining room
     * @param students array of student to be added
     */
    public void addStudentsToDiningRoom(int students[]){
        diningRoom=sumArray(diningRoom,students);
    }

    /**
     * add an array of students to waiting room
     * @param students array of student to be added
     */
    public void addStudentsToWaitingRoom(int students[]){
        waitingRoom=sumArray(waitingRoom,students);
    }

    public void setMaxStudentsInWaiting(int maxStudentsInWaiting) {
        this.maxStudentsInWaiting = maxStudentsInWaiting;
    }

    public boolean[] getCoin3() {
        return coin3;
    }

    /**
     * set if coin bonus for reaching 3 students of the same color in dining room is taken
     * @param student color of the student to be setted true
     */
    public void setCoin3(int student) {
        coin3[student] = true;
    }

    public boolean[] getCoin6() {
        return coin6;
    }

    /**
     * set if coin bonus for reaching 6 students of the same color in dining room is taken
     * @param student color of the student to be setted true
     */
    public void setCoin6(int student) {
        coin6[student] = true;
    }

    public boolean[] getCoin9() {
        return coin9;
    }

    /**
     * set if coin bonus for reaching 9 students of the same color in dining room is taken
     * @param student color of the student to be setted true
     */
    public void setCoin9(int student) {
        coin9[student] = true;
    }
}
