package it.polimi.ingsw;


import java.util.ArrayList;

public class Game {
    private ArrayList<Player> Players = new ArrayList<Player>();
    private int n_Player;
    private boolean Gamemode;
    private Table table;

    /** TODO
        va implementato il costruttore
        va implementato il calcolo dei turni
     */
    public Game(){}


    public int getN_Player() {
        return n_Player;
    }

    public void setN_Player(int n_Player) {
        this.n_Player = n_Player;
    }

    public void setGamemode(boolean gamemode) {
        Gamemode = gamemode;
    }

    public boolean getGamemode() {
        return Gamemode;
    }

    public ArrayList<Player> getPlayers() {
        return Players;
    }

    public void addPlayers(String name) {
            Players.add(new Player(name));
    }
}
