package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Command implements Serializable {
    private boolean dataGathered;
    private boolean isCliClient;
    private Game game;
    private String username;
    private String msg;

    public Command(boolean isCliClient, Game game, String username)
    {
        this.isCliClient = isCliClient;
        this.game = game;
        this.username = username;
        dataGathered = false;
        msg = "";
    }

    public abstract void undo(Game game) throws EriantysExceptions;
    public abstract void  getData() throws EriantysExceptions;
    public abstract boolean execute(Game game) throws EriantysExceptions;
    public abstract String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions;
    public boolean isDataGathered() {
        return dataGathered;
    }

    public boolean isCliClient() {
        return isCliClient;
    }

    public void setCliClient(boolean cliClient) {
        isCliClient = cliClient;
    }

    public void setDataGathered(boolean dataGathered) {
        this.dataGathered = dataGathered;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
