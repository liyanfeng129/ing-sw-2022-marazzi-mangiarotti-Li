package it.polimi.ingsw;

public abstract class EriantysExceptions extends Exception {
    private String message;
    public EriantysExceptions(String msg)
    {
        this.message = msg;
    }
    public String getMessage() {
        return message;
    }
}
