package it.polimi.ingsw;


public class Cloud {
    private int size;
    private int[] students;

    Cloud()
    {
        this.size = 0;
        this.students = new int[5];
    }

    public  void setCloud(int size) throws EriantysExceptions
    {
        if(size==3 || size== 4)
            this.size = size;
        else
            throw new InnerExceptions.NotValidCloudSizeException("Cloud size is not valid");
    }
     //non manca una addStudent?
    //non ci serve una getCloud e una getStudent della cloud?? io la creo ma poi ditemi voi se ha senso
    public int getSize() {
        return size;
    }
    public int[] getStudents() {
        return students;
    }

    public void setStudents(int [] students)
    {
        this.students = students;
    }
}
