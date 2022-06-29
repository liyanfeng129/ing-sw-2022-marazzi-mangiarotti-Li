package it.polimi.ingsw.GUI;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.Game;

import java.util.Random;

public class GUIInfo
{
    private  String serverAddress = "localhost";
    private  String userName = "";
    private  String gameCreatorName = "";
    private  int listeningPortNumber;
    private Game game;
    private GuiListener listener;
    private Command command;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public GuiListener getListener() {
        return listener;
    }

    public void setListener(GuiListener listener) {
        this.listener = listener;
    }

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

    @Override
    public String toString() {
        return "GUIInfo{" +
                "serverAddress='" + serverAddress + '\'' +
                ", userName='" + userName + '\'' +
                ", gameCreatorName='" + gameCreatorName + '\'' +
                ", listeningPortNumber=" + listeningPortNumber +
                ", game=" + game +
                ", listener=" + listener +
                '}';
    }
}

