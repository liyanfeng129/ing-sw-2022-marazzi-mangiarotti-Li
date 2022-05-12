package it.polimi.ingsw.view;

public enum Color
{
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m");

    static final String RESET = "\u001B[0m";


    private String escape;


    Color(String escape)
    {
        this.escape = escape;
    }


    public String getEscape()
    {
        return escape;
    }


    @Override
    public String toString()
    {
        return escape;
    }
}
