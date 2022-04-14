package it.polimi.ingsw;

public class PlayerBoard {
    private int[] Entrance = new int [5];
    private int[] Hall= new int[5];
    private int Tower;

    public int[] getEntrance() {
        return Entrance;
    }

    public void setEntrance(int[] entrance) {
        Entrance = entrance;
    }

    public void putStudentEntrance(int[] cloud){
        Entrance = sumArray(Entrance,cloud);
    }

    public int[] getHall() {
        return Hall;
    }
    public void putStudentAll(int color){
        Hall[color]++;
    }

    public void setStartingTower(int numberOfPlayer) {
        if(numberOfPlayer==3)
            Tower=6;
        else
            Tower=8;
    }

    //invece di fare set e passare int,non è meglio fare aggiungi 1 e rimuovi 1?
    public void addTower(){
        Tower++;
    }
    public void removeTower(){
        Tower--;
    }

    public int[] sumArray(int[] a1, int[] a2){
        for(int i=0;i<a1.length;i++){
            a1[i]=a1[i]+a2[i];
        }
        return a1;
    }


}
