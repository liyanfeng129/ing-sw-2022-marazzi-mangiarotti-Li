package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public abstract class Command implements Serializable {
    private boolean dataGathered;
    private boolean isCliClient;
    private Game game;
    private String username;
    public Command(boolean isCliClient, Game game, String username)
    {
        this.isCliClient = isCliClient;
        this.game = game;
        this.username = username;
        dataGathered = false;
    }
    public abstract void undo() throws EriantysExceptions;
    public abstract void  getData() throws EriantysExceptions;
    public abstract boolean execute(Game game) throws EriantysExceptions;
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
}
