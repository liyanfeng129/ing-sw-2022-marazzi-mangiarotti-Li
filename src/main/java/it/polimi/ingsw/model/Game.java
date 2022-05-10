package it.polimi.ingsw.model;


import it.polimi.ingsw.characterCards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    private ArrayList<Player> players = new ArrayList<Player>();
    private int n_Player;
    private boolean expertMode;
    private Table table;
    private Professors professors;
    private boolean gameStarted;
    private List<Player> turnList;

    //secondo me ci vorrebbe un exception nel caso passo player con modalita di gioco diversa
    public Game(int n_Player, boolean expertMode, Player creator) throws EriantysExceptions {
        this.n_Player = n_Player;
        this.expertMode = expertMode;
        players.add(creator);
        table = new Table();
        professors = new Professors();
        this.gameStarted = false;
    }

    public List<Player> getTurnList() {
        return turnList;
    }

    public void setTurnList(List<Player> turnList) {
        this.turnList = turnList;
    }

    public Table getTable() {
        return table;
    }

    //secondo me manca un exception nel caso in cui i giocatori avessero lo stesso colore di torre
    public void startGame() throws EriantysExceptions {
        ArrayList<Cloud> clouds = new ArrayList<>();
        ArrayList<CharacterCard> characterCards = new ArrayList<>();

        if(players.size() != n_Player)
            throw new InnerExceptions.GameStartingError("The number of player is incorrect.");

                if(n_Player == 2 && expertMode)
                {
                    /**
                     * TODO
                     * */
                }
                if(n_Player == 3 &&expertMode)
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
                    this.table.tableInit(clouds,null);
                    for(int i = 0; i< players.size(); i++)
                        this.players.get(i).getPb().setWaitingRoom(table.getBag().draw(7));
                }

                if(n_Player == 3 && !expertMode)
                {
                    for(int i = 0; i < n_Player; i++)
                    {
                        Cloud cloud = new Cloud();
                        cloud.setCloud(4);
                        clouds.add(cloud);
                    }
                    table.tableInit(clouds,null);
                }
                if(n_Player == 4 && !expertMode)
                {
                    for(int i = 0; i < n_Player; i++)
                    {
                        Cloud cloud = new Cloud();
                        cloud.setCloud(3); // era 4? mi sembra fosse 3
                        clouds.add(cloud);
                    }
                    table.tableInit(clouds,null);
                }
                this.gameStarted = true;
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
        return players;
    }

    public void addPlayers(Player player) throws EriantysExceptions {
        if(players.size()>n_Player)
            throw new InnerExceptions.InvalidPlayerNumberException("too many players");
            players.add(player);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public Professors getProfessors() {
        return professors;
    }

    @Override
    public String toString() {
        return "Game{" +
                "Players=" + players +
                ", n_Player=" + n_Player +
                ", expertMode=" + expertMode +
                ", table=" + table +
                ", gameStarted=" + gameStarted +
                '}';
    }

}

