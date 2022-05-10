package it.polimi.ingsw.command;

import it.polimi.ingsw.model.Game;

public abstract class Command {
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
    public void undo() {}
    public void  getData() {};
    public void execute() {};
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
}
