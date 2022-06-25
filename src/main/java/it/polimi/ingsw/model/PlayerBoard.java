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


    //ma perche devo passare un array di student se poi lo inizializzi a 0?
    //si potrebbe migliorare il costruttore settando direttamente il numero di torri in base alla partita
    public PlayerBoard(int n_tower,TowerColor tower_color, int maxStudentsInWaiting) {
        this.diningRoom = new int[5];
        this.waitingRoom = new int[5];
        this.profHolder = new Boolean[5];
        for(int i = 0; i < 5; i++)
        {
            this.diningRoom[i] = 0;
            this.waitingRoom[i] = 0;
            this.profHolder[i] = false;
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

    public void setWaitingRoom(int[] waitingRoom) throws EriantysExceptions {
        if(Arrays.stream(waitingRoom).sum() > maxStudentsInWaiting || Arrays.stream(waitingRoom).sum() <0)
            throw new InnerExceptions.NotValidStudentSizeException("Number of student is not valid");
        this.waitingRoom = waitingRoom.clone();
    }
    public void addCloudToWaitingRoom(int[] cloud){
        waitingRoom = sumArray(waitingRoom, cloud);
    }

    public int getN_tower() {
        return n_tower;
    }

    // nella set tower devo passare per forza 6 o 8 giusto?
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

    public void moveTower(int n)
    {
        n_tower += n;
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
        if((this.diningRoom[student]+1)>10)
            throw new InnerExceptions.NotValidStudentSizeException("Number of student is already at max");
        this.diningRoom[student] ++;
    }

    public  void takeStudentFromHolder(int student) throws InnerExceptions.NegativeValue {
        if(this.diningRoom[student]==0)
            throw new InnerExceptions.NegativeValue("Not Enought student");
        this.diningRoom[student] --;
    }

    public int getMaxStudentsInWaiting() {
        return maxStudentsInWaiting;
    }

    public void removeStudentFromWaitingRoom(int students[]){
        for(int i = 0; i < 5; i++)
            this.waitingRoom[i] -= students[i];
    }

    public void removeStudentFromDiningRoom(int students[]){
        for(int i = 0; i < 5; i++)
            this.diningRoom[i] -= students[i];
    }

    public void addStudentsToDiningRoom(int students[]){
        diningRoom=sumArray(diningRoom,students);
    }

    public void addStudentsToWaitingRoom(int students[]){
        waitingRoom=sumArray(waitingRoom,students);
    }

    public void setMaxStudentsInWaiting(int maxStudentsInWaiting) {
        this.maxStudentsInWaiting = maxStudentsInWaiting;
    }

    public boolean[] getCoin3() {
        return coin3;
    }

    public void setCoin3(int student) {
        coin3[student] = true;
    }

    public boolean[] getCoin6() {
        return coin6;
    }

    public void setCoin6(int student) {
        coin6[student] = true;
    }

    public boolean[] getCoin9() {
        return coin9;
    }

    public void setCoin9(int student) {
        coin9[student] = true;
    }
}
