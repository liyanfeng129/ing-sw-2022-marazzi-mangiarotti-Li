package it.polimi.ingsw.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GameInfo implements Serializable {
    private ArrayList<String> players;
    private boolean expertMode;
    private String gameStartingTime;

    public GameInfo(ArrayList<String> players, boolean expertMode, String date) {
        this.players = players;
        this.expertMode = expertMode;
        this.gameStartingTime = date;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public String getGameStartingTime() {
        return gameStartingTime;
    }

    public void setGameStartingTime(String gameStartingTime) {
        this.gameStartingTime = gameStartingTime;
    }
}
