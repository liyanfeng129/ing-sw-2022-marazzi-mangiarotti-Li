package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Game;

import java.util.Random;

public class GUIInfo
{
    private  String serverAddress = "localhost";
    private  String userName = "";
    private  String gameCreatorName = "";
    private  int listeningPortNumber;
    private Game game;
    public GUIInfo() {
        listeningPortNumber = new Random().nextInt(65353);
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getListeningPortNumber() {
        return listeningPortNumber;
    }

    public void setListeningPortNumber(int listeningPortNumber) {
        this.listeningPortNumber = listeningPortNumber;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getGameCreatorName() {
        return gameCreatorName;
    }

    public void setGameCreatorName(String gameCreatorName) {
        this.gameCreatorName = gameCreatorName;
    }
}

