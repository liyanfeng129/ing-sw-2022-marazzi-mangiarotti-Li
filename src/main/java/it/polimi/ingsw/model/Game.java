package it.polimi.ingsw.model;


import it.polimi.ingsw.characterCards.CharacterCard;
import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.state.PlanningState;
import it.polimi.ingsw.state.State;

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
    private ArrayList<Command> commands = new ArrayList<>();
    private State gameState;
    private ArrayList<Player> turnList  = new ArrayList<Player>();

    //secondo me ci vorrebbe un exception nel caso passo player con modalita di gioco diversa
    public Game(int n_Player, boolean expertMode, Player creator) throws EriantysExceptions {
        this.n_Player = n_Player;
        this.expertMode = expertMode;
        players.add(creator);
        table = new Table();
        professors = new Professors();
        this.gameStarted = false;

    }

    public synchronized Player findPlayerByName(String name) throws EriantysExceptions
    {
        for(Player p : players)
            if(p.getName().equals(name))
                return p;
        throw new InnerExceptions.NoSuchUserException("Player not found");
    }

    public synchronized Command getLastCommand()
    {
        return this.commands.get(commands.size()-1);
    }
    public synchronized void setLastCommand(Command command)
    {
        this.commands.remove(commands.size()-1);
        this.commands.add(command);
    }
    public synchronized void removeLastCommand()
    {
        this.commands.remove(commands.size()-1);
    }

    public synchronized void addCommand(Command command) { commands.add(command); }

    public synchronized State getGameState()
    {
        return this.gameState;
    }

    public synchronized void changeGameState(State state)
    {
        this.gameState = state;
    }


    public synchronized ArrayList<Player> getTurnList() {
        return turnList;
    }

    public synchronized void setTurnList(ArrayList<Player> turnList) {
        this.turnList = turnList;
    }



    public synchronized Table getTable() {
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
                this.gameState = new PlanningState(this, 0);
                this.turnList = players;
                this.commands.add(this.gameState.generateCommand());
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

    public synchronized ArrayList<Player> getPlayers() {
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public void setProfessors(Professors professors) {
        this.professors = professors;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void setGameState(State gameState) {
        this.gameState = gameState;
    }
}

