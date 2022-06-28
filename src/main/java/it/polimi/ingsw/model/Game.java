package it.polimi.ingsw.model;


import it.polimi.ingsw.characterCards2.*;
import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.UseCharacterCommand;
import it.polimi.ingsw.state.PlanningState;
import it.polimi.ingsw.state.State;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private UseCharacterCommand usedCharacter = null;
    private String gameStartingTime;

    /**
     * constructor of Game
     * @param n_Player initialioze the number of player in this game
     * @param expertMode initialize the type of this game
     * @param creator initialize the player who started this game
     * @throws EriantysExceptions if constructor of tsble throw an exception
     */
    public Game(int n_Player, boolean expertMode, Player creator) throws EriantysExceptions {
        this.n_Player = n_Player;
        this.expertMode = expertMode;
        players.add(creator);
        table = new Table();
        professors = new Professors();
        this.gameStarted = false;

    }

    /**
     * TODO yan
     * non so a cosa serva,spiegalo te
     */
    public Game()
    {

    }

    /**
     * find the player selected among all the players in game
     * @param name the name of the player this method has to find
     * @return the player which name belong to
     * @throws EriantysExceptions if there is no player with this name in game
     */
    public synchronized Player findPlayerByName(String name) throws EriantysExceptions
    {
        for(Player p : players)
            if(p.getName().equals(name))
                return p;
        throw new InnerExceptions.NoSuchUserException("Player not found");
    }

    /**
     * get the command in last position in ArrayList commands
     * @return the command in last position in arrayList commands
     */
    public synchronized Command getLastCommand()
    {
        return this.commands.get(commands.size()-1);
    }

    /**
     * delete command in last position of ArrayList commands and set this command
     * @param command the command to be setted in last position
     */
    public synchronized void setLastCommand(Command command)
    {
        this.commands.remove(commands.size()-1);
        this.commands.add(command);
    }

    /**
     * remove first command from ArrayList commnad if the size of commnads is >= 2
     */
    public synchronized void removeCommand()
    {
        if(commands.size() >= 2)
            this.commands.remove(0);
    }

    /**
     * get the command to be executed
     * @return the command in first position in ArrayList commands
     */
    public synchronized Command getExecutedCommand()
    {
        return commands.get(0);
    }

    /**
     * add a command in ArrayList commands
     * @param command the command to be added
     */
    public synchronized void addCommand(Command command) { commands.add(command); }

    public synchronized State getGameState()
    {
        return this.gameState;
    }

    public synchronized void changeGameState(State state)
    {
        this.gameState = state;
    }

    /**
     * @return in which order the players have to make a play in this turn
     */
    public synchronized ArrayList<Player> getTurnList() {
        return turnList;
    }

    public synchronized void setTurnList(ArrayList<Player> turnList) {
        this.turnList = turnList;
    }



    public synchronized Table getTable() {
        return table;
    }

    /**
     * this method is called to start the game
     * this method initialize cloud and character according to the type and number of player of the game
     * @throws EriantysExceptions if the number of players in the game is not allowed by the rules
     */
    public void startGame() throws EriantysExceptions {
        ArrayList<Cloud> clouds = new ArrayList<>();
        ArrayList<CharacterCard> characterCards = new ArrayList<>();

        if(players.size() != n_Player)
            throw new InnerExceptions.GameStartingError("The number of player is incorrect.");

                if(n_Player == 2 && expertMode)
                {
                    for(int i = 0; i < n_Player; i++)
                    {
                        Cloud cloud = new Cloud();
                        cloud.setCloud(3);
                        clouds.add(cloud);
                    }
                    table.initCharacter();
                    characterCards.add(table.getCharacters().get(0));
                    characterCards.add(table.getCharacters().get(1));
                    characterCards.add(table.getCharacters().get(2));

                    this.table.tableInit(clouds);
                    for(int i = 0; i< players.size(); i++)
                    {
                        Player p =   this.players.get(i);
                        p.getPb().setWaitingRoom(table.getBag().draw(7));
                        p.setWallet(new Wallet());
                    }
                }
                if(n_Player == 3 &&expertMode)
                {
                    for(int i = 0; i < n_Player; i++)
                    {
                        Cloud cloud = new Cloud();
                        cloud.setCloud(4);
                        clouds.add(cloud);
                    }
                    table.initCharacter();
                    characterCards.add(table.getCharacters().get(0));
                    characterCards.add(table.getCharacters().get(1));
                    characterCards.add(table.getCharacters().get(2));

                    this.table.tableInit(clouds);
                    for(int i = 0; i< players.size(); i++)
                    {
                        Player p =   this.players.get(i);
                        p.getPb().setWaitingRoom(table.getBag().draw(9));
                        p.setWallet(new Wallet());
                    }
                }
                if(n_Player == 2 && !expertMode)
                {
                    for(int i = 0; i < n_Player; i++)
                    {
                        Cloud cloud = new Cloud();
                        cloud.setCloud(3);
                        clouds.add(cloud);
                    }
                    this.table.tableInit(clouds);
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
                    table.tableInit(clouds);
                    for(int i = 0; i< players.size(); i++)
                        this.players.get(i).getPb().setWaitingRoom(table.getBag().draw(9));
                }
                this.gameStarted = true;
                this.gameState = new PlanningState(this, 0);
                this.turnList = players;
                this.commands.add(this.gameState.generateCommand());
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                this.gameStartingTime = dateFormat.format(new Date());
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

    /**
     * add a player in the game
     * @param player the player to be added
     * @throws EriantysExceptions if the max number of players for this game is already reached
     */
    public void addPlayers(Player player) throws EriantysExceptions {
        if(players.size()>=n_Player)
            throw new InnerExceptions.InvalidPlayerNumberException("too many players");
            players.add(player);
    }
    /**
     * add a player in the TurnList
     * @param player the player to be added
     * @throws EriantysExceptions if the max number of players in this turn is already reached
     */
    public void addPlayerToTurnList(Player player) throws EriantysExceptions {
        if(players.size()>=n_Player)
            throw new InnerExceptions.InvalidPlayerNumberException("too many players");
        turnList.add(player);
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

    /*public void setGameState(State gameState) {
        this.gameState = gameState;
    }*/

    public UseCharacterCommand getUsedCharacter() {
        return usedCharacter;
    }

    public void setUsedCharacter(UseCharacterCommand usedCharacter) {
        this.usedCharacter = usedCharacter;
    }

    public String getGameStartingTime() {
        return gameStartingTime;
    }

    public void setGameStartingTime(String gameStartingTime) {
        this.gameStartingTime = gameStartingTime;
    }
}


