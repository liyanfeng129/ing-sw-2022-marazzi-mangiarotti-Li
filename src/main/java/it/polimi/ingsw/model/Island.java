package it.polimi.ingsw.model;


import java.io.Serializable;
import java.util.Arrays;

public class Island implements Serializable {
    private final int[] students;
    private boolean mother_nature;
    private int size;
    private TowerColor towerColor;
    private boolean NoEntryTiles;

    /**
     * constructor for class Island
     */
    public Island() {

        this.students = new int[5];
        this.students[SType.RED.ordinal()] = 0;
        this.students[SType.YELLOW.ordinal()] = 0;
        this.students[SType.PINK.ordinal()] = 0;
        this.students[SType.BLUE.ordinal()] = 0;
        this.students[SType.GREEN.ordinal()] = 0;
        this.towerColor = null;
        this.mother_nature = false;
        this.NoEntryTiles=false;
        this.size = 1;
    }
    public int[] getStudents(){
        return this.students;
    }

    /**
     * add a student to island
     * @param color color of the student to be added
     */
    public void addStudent(int color){
        this.students[color]++;
    }

    /**
     * sum an array of student to this island
     * @param students array of student to be added
     */
    public void mergeStudents(int[] students)
    {
        for(int i = 0; i < 5; i++)
            this.students[i] = this.students[i] + students[i];
    }

    public boolean getMotherNature(){
        return this.mother_nature;
    }

    public void setMotherNature(boolean mother_nature){this.mother_nature = mother_nature; }

    public void setTowerColor(TowerColor towerColor){
        this.towerColor = towerColor;
    }

    public TowerColor getTowerColor() {
        return this.towerColor;
    }

    /**
     * increase the size of this island
     * @param size the size to be added at the current size of this island
     */
    public void IncreasingSize(int size)
    {
        this.size = this.size + size;
    }

    public int getSize()
    {
        return this.size;
    }

    public void setNoEntryTiles(boolean noEntryTiles) {
        NoEntryTiles = noEntryTiles;
    }

    public boolean isNoEntryTiles() {
        return NoEntryTiles;
    }

    @Override
    public String toString() {
        return "Island{" +
                "students=" + Arrays.toString(students) +
                ", mother_nature=" + mother_nature +
                ", size=" + size +
                ", towerColor=" + towerColor +
                '}';
    }
}
