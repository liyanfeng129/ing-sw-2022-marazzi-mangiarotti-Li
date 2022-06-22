package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

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
    public abstract String GUIGetData(ArrayList<Object> inputs);
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

    public void addCoin(Player p){
        for (int i=0;i<5;i++) {
            if (p.getPb().getDiningRoom()[i] >= 3 && p.getPb().getCoin3()[i] == false) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin3(i);
            }
            if (p.getPb().getDiningRoom()[i] >= 6 && p.getPb().getCoin6()[i] == false) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin6(i);
            }
            if (p.getPb().getDiningRoom()[i] >= 9 && p.getPb().getCoin9()[i] == false) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin9(i);
            }
        }
    }

}
