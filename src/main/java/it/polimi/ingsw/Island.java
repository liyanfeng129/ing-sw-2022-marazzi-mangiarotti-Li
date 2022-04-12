package it.polimi.ingsw;


public class Island {
    private final int[] students;
   // private Island inext_island;
    private boolean mother_nature;
    private int size;
    private String tower;

    //public Island(Island inext_island) {
    public Island() {

        this.students = new int[5];
        this.students[SType.RED.ordinal()] = 0;
        this.students[SType.YELLOW.ordinal()] = 0;
        this.students[SType.PINK.ordinal()] = 0;
        this.students[SType.BLUE.ordinal()] = 0;
        this.students[SType.GREEN.ordinal()] = 0;
        this.tower = null;
        //this.inext_island = inext_island;
        this.mother_nature = false;
        this.size = 1;
    }
    public int[] getStudents(){
        return this.students;
    }

    public void addStudent(int color){
        this.students[color]++;
    }

    public void mergeStudents(int[] students)
    {
        for(int i = 0; i < 5; i++)
            this.students[i] = this.students[i] + students[i];
    }

    public boolean getMotherNature(){
        return this.mother_nature;
    }

    public void setMotherNature(boolean mother_nature){this.mother_nature = mother_nature; }

    public void setTower(String color){
        this.tower = color;
    }

    public String getTower() {
        return this.tower;
    }

    public void IncreasingSize(int size)
    {
        this.size = this.size + size;
    }

    public int getSize()
    {
        return this.size;
    }
}
