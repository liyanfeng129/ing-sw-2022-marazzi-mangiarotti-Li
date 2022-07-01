package it.polimi.ingsw.command;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

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

    /**
     * this method get the inputs for the action the current player has to make in cli
     * @throws EriantysExceptions
     */
    public abstract void  getData() throws EriantysExceptions;

    /**
     * this method execute the action depending on which state the game is in at the moment
     * @param game
     * @return true if action correctly executed
     * @throws EriantysExceptions
     */
    public abstract boolean execute(Game game) throws EriantysExceptions;

    /**
     * this method get the inputs for the action the current player has to make in gui
     * @throws EriantysExceptions
     */
    public abstract String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions;
    public boolean isDataGathered() {
        return dataGathered;
    }

    public boolean isCliClient() {
        return isCliClient;
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

    /**
     * this method is used to get the inputs command line
     * if an input is not an integer this method ask again for another input
     * @return input from cli
     */
    public int getInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = input.replaceAll("\\D+","");
        while (input.length()==0){
            System.out.println("Pleas input an integer");
            input = scanner.nextLine();
            input = input.replaceAll("\\D+","");
        }
        return Integer.parseInt(input);
    }

    /**
     * this method compute if player p has reached 3,6 or 9 students of the same color in his playerboard
     * for the first time.
     * In this case 1 coin is added to this player's saving
     * @param p player to check
     */
    public void addCoin(Player p){
        for (int i=0;i<5;i++) {
            if (p.getPb().getDiningRoom()[i] >= 3 && !p.getPb().getCoin3()[i]) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin3(i);
            }
            if (p.getPb().getDiningRoom()[i] >= 6 && !p.getPb().getCoin6()[i]) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin6(i);
            }
            if (p.getPb().getDiningRoom()[i] >= 9 && !p.getPb().getCoin9()[i]) {
                p.getWallet().addCoin(1);
                p.getPb().setCoin9(i);
            }
        }
    }

}
