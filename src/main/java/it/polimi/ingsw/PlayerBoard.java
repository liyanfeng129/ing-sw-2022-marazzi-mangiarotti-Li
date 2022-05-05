package it.polimi.ingsw;

import java.io.Serializable;

public class PlayerBoard implements Serializable {
    private int[] studentsHolder;
    private int[] waitingRoom;
    private int tower;
    private int n_tower;
    private Boolean[] profHolder;
    private int maxStudentsInWaiting;

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

    public void setStudentsHolder(int[] studentsHolder) {
        this.studentsHolder = studentsHolder;
    }

    public int[] getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(int[] waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    public int getTower() {
        return tower;
    }

    public void setTower(int tower) {
        this.tower = tower;
    }

    public Boolean[] getProfHolder() {
        return profHolder;
    }

    public void setProfHolder(Boolean[] profHolder) {
        this.profHolder = profHolder;
    }

    public void moveTower(int n) throws EriantysExceptions
    {
        if(tower+n < 0 || tower +n > n_tower)
            throw new InnerExceptions.InvalidTowerNumberException("Invalid tower number exception!");

        tower += n;
    }

    public void setProf(int prof, boolean occupy)
    {
        this.profHolder[prof] = occupy;
    }


    private int[] sumArray(int[] a1, int[] a2){
        for(int i=0;i<a1.length;i++){
            a1[i]=a1[i]+a2[i];
        }
        return a1;
    }

    public void takeStudentFromWaitingRoom(int student)
    {
        this.waitingRoom[student] -- ;
    }

    public  void takeStudentFromHolder(int student)
    {
        this.studentsHolder[student] --;
    }





}
