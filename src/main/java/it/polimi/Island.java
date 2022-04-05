package it.polimi;
import java.util.HashMap;
import EnumSet;



public class Island {
    private enum stuTY;
    //private HashMap<String, int> students;
    private int[] students;
    private Island inext_island;
    private boolean mother_nature;
    private int size;
    private String tower;
    private enum SType {
        RED,
        YELLOW,
        PINK,
        BLUE,
        GREEN
    }

    const int RED = 0;
    const int YELLOW = 1;
    const int PINK = 2;
    const int BLUE = 3;
    const int GREEN = 4;

    // Sarebbe fatto fuori

    public Island(Island inext_island) {
        //this.stuTY = EnumSet.StudentType;
        this.students = new int[5];
        this.students[RED] = 0;
        this.students[YELLOW] = 0;
        this.students[PINK] = 0;
        this.students[BLUE] = 0;
        this.students[GREEN] = 0;
        this.tower = null;
        this.inext_island = inext_island;
        this.mother_nature = false;
        this.size = 1;
    }
    public int[] getStutents(){
        return this.students;
    }

    public void addStudent(int color){
        this.students[color]++;
    }

    public boolean getMotherNature(){
        return this.mother_nature;
    }

    public void setTower(String color){
        this.tower = color;
    }

    public String getTower() {
        return this.tower;
    }
}
