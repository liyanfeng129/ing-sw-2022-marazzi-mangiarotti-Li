package it.polimi.ingsw;


public class Cloud {
    private int size;
    private final int[] students;

    Cloud()
    {
        this.size = 0;
        this.students = new int[5];
    }

    public  void setCloud(int size) throws EriantysExceptions
    {
        if(size!=3 || size!= 4)
            throw new InnerExceptions.NotValidCloudSizeException("Cloud size is not valid");
        this.size = size;
    }
}
