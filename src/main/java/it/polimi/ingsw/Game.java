package it.polimi.ingsw;


import it.polimi.ingsw.characterCards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private ArrayList<Player> Players = new ArrayList<Player>();
    private int n_Player;
    private boolean expertMode;
    private Table table;
    private boolean gameStarted;

    public Game(int n_Player, boolean expertMode, Player creator)
    {
        this.n_Player = n_Player;
        this.expertMode = expertMode;
        Players.add(creator);
        table = null;
        this.gameStarted = false;
    }

    public Table getTable() {
        return table;
    }

    public void startGame() throws EriantysExceptions {
        ArrayList<Cloud> clouds = new ArrayList<>();
        ArrayList<CharacterCard> characterCards = new ArrayList<>();

        if(Players.size() != n_Player)
            throw new InnerExceptions.GameStartingError("The number of player is incorrect.");

                if(n_Player == 2 && expertMode)
                {
                    /**
                     * TODO
                     * */
                }
                if(n_Player == 4 &&expertMode)
                {
                    /**
                     * TODO
                     * */
                }
                if(n_Player == 2 && !expertMode)
                {
                    for(int i = 0; i < n_Player; i++)
                    {
                        Cloud cloud = new Cloud();
                        cloud.setCloud(3);
                        clouds.add(cloud);
                    }
                    table.tableInit(clouds,null);
                }

                if(n_Player == 4 && !expertMode)
                {
                    for(int i = 0; i < n_Player; i++)
                    {
                        Cloud cloud = new Cloud();
                        cloud.setCloud(4); // era 4?
                        clouds.add(cloud);
                    }
                    table.tableInit(clouds,null);
                }
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getN_Player() {
        return n_Player;
    }

    public void setN_Player(int n_Player) {
        this.n_Player = n_Player;
    }


    public boolean isExpertMode() {
        return expertMode;
    }

    public ArrayList<Player> getPlayers() {
        return Players;
    }

    public void addPlayers(Player player) {
            Players.add(player);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    @Override
    public String toString() {
        return "Game{" +
                "Players=" + Players +
                ", n_Player=" + n_Player +
                ", expertMode=" + expertMode +
                ", table=" + table +
                ", gameStarted=" + gameStarted +
                '}';
    }
}

