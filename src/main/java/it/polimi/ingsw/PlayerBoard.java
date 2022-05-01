package it.polimi.ingsw;

import java.util.Arrays;

public class PlayerBoard {
    private int[] studentsHolder;
    private int[] waitingRoom;
    private int tower;
    private int n_tower;
    private Boolean[] profHolder;
    private int maxStudentsInWaiting;

    //ma perche devo passare un array di student se poi lo inizializzi a 0?
    public PlayerBoard(int tower, int[] students, int maxStudentsInWaiting) {
        this.studentsHolder = new int[5];
        this.waitingRoom = new int[5];
        this.profHolder = new Boolean[5];
        for(int i = 0; i < 5; i++)
        {
            this.studentsHolder[i] = 0;
            this.waitingRoom[i] = 0;
            this.profHolder[i] = false;
        }
        this.tower = tower;
        this.n_tower = tower;
        this.maxStudentsInWaiting = maxStudentsInWaiting;
        this.waitingRoom = sumArray(this.waitingRoom, students);

    }

    public int[] getStudentsHolder() {
        return studentsHolder;
    }

    public void setStudentsHolder(int[] studentsHolder) throws EriantysExceptions {
        for (int i=0;i<5;i++){
            if(studentsHolder[i]>10 || studentsHolder[i]<0)
                throw new InnerExceptions.NotValidStudentSizeException("Number of student is not valid");
        }
        this.studentsHolder = studentsHolder;
    }

    public int[] getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(int[] waitingRoom) throws EriantysExceptions {
        if(Arrays.stream(waitingRoom).sum() > maxStudentsInWaiting || Arrays.stream(waitingRoom).sum() <0)
            throw new InnerExceptions.NotValidStudentSizeException("Number of student is not valid");
        this.waitingRoom = waitingRoom;
    }

    public int getTower() {
        return tower;
    }

    // nella set tower devo passare per forza 6 o 8 giusto?
    public void setTower(int tower) throws EriantysExceptions {
        if(tower != n_tower)
            throw new InnerExceptions.InvalidTowerNumberException("Invalid tower number exception!");

        this.tower = tower;
    }


    //secondo me non servono, ho un array a parte che mi dice chi ha i professori
    public Boolean[] getProfHolder() {
        return profHolder;
    }
    public void setProfHolder(Boolean[] profHolder) {
        this.profHolder = profHolder;
    }
    public void setProf(int prof, boolean occupy)
    {
        this.profHolder[prof] = occupy;
    }

    public void moveTower(int n) throws EriantysExceptions
    {
        if(tower+n < 0 || tower +n > n_tower)
            throw new InnerExceptions.InvalidTowerNumberException("Invalid tower number exception!");

        tower += n;
    }



    private int[] sumArray(int[] a1, int[] a2){
        for(int i=0;i<a1.length;i++){
            a1[i]=a1[i]+a2[i];
        }
        return a1;
    }

    public void takeStudentFromWaitingRoom(int student) throws EriantysExceptions
    {
        if((this.waitingRoom[student]-1)<0)
            throw new InnerExceptions.NegativeValue("Negative Value");
        this.waitingRoom[student] -- ;
    }
    public  void addStudentToHolder(int student) throws EriantysExceptions
    {
        if((this.studentsHolder[student]+1)>10)
            throw new InnerExceptions.NotValidStudentSizeException("Number of student is already at max");
        this.studentsHolder[student] ++;
    }

    //secondo me non serve
    public  void takeStudentFromHolder(int student)
    {
        this.studentsHolder[student] --;
    }





}
