package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.client.User;
import it.polimi.ingsw.client.Users;
import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class EriantysClientHandler extends Thread{
    BufferedReader in;
    PrintWriter out;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Socket client;
    ArrayList<Game> games;
    Player player;
    Game game;
    public EriantysClientHandler(Socket client,ArrayList<Game> games)
    {
        this.client=client;
        this.games = games;

    }

    public void run()
    {
        try {
            //out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            //in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             oos = new ObjectOutputStream(client.getOutputStream());
             ois=new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(">> "+client.getInetAddress()+" is connected" +"\n");

        try
        {
            ArrayList<Object> messages = (ArrayList<Object>) ois.readObject();
            ArrayList<Object> responses = new ArrayList<>();
            String request = (String) messages.get(0);
            Game game;
            String userName;
            String msg;
            boolean cliClient;
            boolean hasACommand;
            switch (request)
            {
                case Config.GET_NEWEST_GAME:
                    System.out.println(client+"tries to get a updated game");
                    userName = (String) messages.get(1);
                    game = findGameForPlayer(userName);
                    responses.add(Config.GET_NEWEST_GAME_SUC);
                    responses.add(game);
                    oos.writeObject(responses);
                    break;
                case Config.CREATE_GAME_FOR_2:
                    System.out.println(client+"tries to create a game for two");
                    userName = (String) messages.get(1);
                    cliClient = (boolean) messages.get(2);
                    msg = createGameFor2(userName,cliClient);
                    game = findGameForPlayer((String) messages.get(1));
                    responses.add(msg);
                    responses.add(game);
                    oos.writeObject(responses);
                    break;

                case Config.SHOW_EXISTING_GAMES:
                    System.out.println(client+"wants a list of games");
                    ArrayList<Game> gameList = showGames();
                    if(gameList.size() > 0)
                    {
                        responses.add(Config.SHOW_EXISTING_GAMES_SUC);
                        responses.add(gameList);
                    }
                    else
                        responses.add("No game created yet, you should create one.");
                    oos.writeObject(responses);
                    break;
                case Config.JOIN_ONE_GAME:
                    System.out.println(client+"tries to join a game");
                    String gameRoom = (String) messages.get(1);
                    String playerName = (String) messages.get(2);
                    cliClient = (boolean) messages.get(3);
                    msg = joinAGame(gameRoom, playerName, cliClient);
                    if(msg.equals(Config.JOIN_ONE_GAME_SUC))
                    {
                        game = findGameForPlayer(playerName);
                        responses.add(msg);
                        responses.add(game);
                        oos.writeObject(responses);
                        gameUpdate(game);
                    }
                    else
                    {
                        responses.add(msg);
                        oos.writeObject(responses);
                    }
                    break;
                case Config.USER_LOGGING:
                    System.out.println(client+" asking for logging");
                    userName = (String) messages.get(1);
                    String res=logging(userName);
                    responses.add(res);
                    oos.writeObject(responses);
                    break;
                case "try to connect":
                    System.out.println(client+" trying to connect");
                    out.println("ok, you are connected");
                    break;
                case "try to get a game":
                    System.out.println(client+" testing for get a game");
                    game = findGameForPlayer((String) messages.get(1));
                    responses.add("ok");
                    responses.add(game);
                    oos.writeObject(responses);
                    gameUpdate(game);
                    break;
                case "try to modify game":
                    System.out.println(client+" testing for modifying game");
                    game = findGameForPlayer((String) messages.get(1));
                    game.setGameStarted(false);
                    responses.add("ok");
                    responses.add(game);
                    oos.writeObject(responses);
                    gameUpdate(game);
                    break;
                case Config.LOG_OUT:
                    System.out.println(client+" asking for logout");
                    responses.add(logOutUser((String) messages.get(1)));
                    oos.writeObject(responses);
                    break;
                case Config.GAME_START:
                    System.out.println(client+" wants to start a game");
                    findGameForPlayer((String) messages.get(1)).startGame();
                    game = findGameForPlayer((String) messages.get(1));
                    responses.add(Config.GAME_START_SUC);
                    oos.writeObject(responses);
                    gameUpdate(game);
                    break;
                case Config.COMMAND_EXECUTE:
                    System.out.println(client+"tries to execute a command");
                    game = findGameForPlayer((String) messages.get(1));
                    game.setLastCommand((Command) messages.get(2));
                    game.getGameState().executeCommand();
                    game.addCommand(game.getGameState().generateCommand());
                    responses.add(Config.COMMAND_EXECUTE_SUC);
                    oos.writeObject(responses);
                    gameUpdate(game);
                    break;
                case Config.LISTENING_FOR_UPDATE:
                    System.out.println(client+" is listening for update");
                    String name = (String) messages.get(1);
                    player = findPlayerByName(name);
                    while(!client.isClosed())
                    {
                        while(!player.isUpdate())
                        {
                            sleep(500);
                        }
                        if(!findGameForPlayer(name).isGameStarted()) // game hasn't started yet
                        {
                            if(findGameForPlayer(name).getPlayers().get(0).getName().equals(name))// this player is creator
                            {
                                System.out.println(player.getName()+"'s game room has been changed");
                                responses = new ArrayList<>();
                                responses.add(Config.UPDATE_CREATOR_WAITING_ROOM);
                            }
                            else
                            {
                                System.out.println(player.getName()+", the room he is in has been changed");
                                responses = new ArrayList<>();
                                responses.add(Config.UPDATE_OTHER_WAITING_ROOM);
                            }
                        }
                        else // Game started
                        {
                            System.out.println(player.getName()+"'s game has been updated");
                            responses = new ArrayList<>();
                            responses.add(Config.GAME_UPDATED);
                        }
                        responses.add(findGameForPlayer(player.getName()));
                        oos.writeObject(responses);
                        player.setUpdate(false);
                    }
                    break;
                default:
                    out.println("not ok, no option valid for this");
            }
            // close client
            client.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private synchronized void gameUpdate(Game game)
    {
        for(Player p : game.getPlayers())
            p.setUpdate(true);
    }
    /**
     * TODO
     *
     * */
    private synchronized String joinAGame(String gameName,String player, boolean cliClient) throws EriantysExceptions {
        Game game = findGameForPlayer(gameName);
        if(game.isGameStarted())
            return "Cannot join a started game";
        if(game.getPlayers().size() == game.getN_Player())
            return "Cannot join a game where is full";
        if(game.getN_Player() == 2 && !game.isExpertMode())
        {
            game.addPlayers(new Player(player,Mage.MAGE2,TowerColor.WHITE,2,true,cliClient));
        }
            return Config.JOIN_ONE_GAME_SUC;
    }

    private synchronized String createGameFor2(String userName, boolean cliClient) throws EriantysExceptions {
        Game game = new Game(2, false, new Player(userName,Mage.MAGE1,TowerColor.BLACK,2,true,cliClient));
        games.add(game);
        return Config.CREATE_GAME_FOR_2_SUC;
    }

    private synchronized ArrayList<Game> showGames()
    {
        ArrayList<Game> notStartedGame = new ArrayList<>();
        for(Game g : games)
            if(!g.isGameStarted())
                notStartedGame.add(g);
        return notStartedGame;
    }

    private synchronized String logOutUser(String userName) throws EriantysExceptions {
        Users userList = (Users) fileJason2Object("users.jason", Users.class);
        userList.logOutUser(userName);
        object2FileJason("users.jason", userList);
        //out.println(Config.LOG_OUT_SUC);
        return Config.LOG_OUT_SUC;
    }

    private synchronized String logging(String userName) throws EriantysExceptions
    {
        Users users = (Users) fileJason2Object("users.jason", Users.class);
        if(users.contains(userName))
        {
            if(!users.getUser(userName).isLogged()) //this user is not logged
            {
                users.logUser(userName);
                object2FileJason("users.jason", users);
                //out.println(Config.USER_LOGGED);
                return Config.USER_LOGGED;
            }
            else // this userName is already logged
            {
                //out.println(Config.USER_ALREADY_LOGGED);
                return Config.USER_ALREADY_LOGGED;
            }
        }
        else // this user is just added in usersList and logged
        {
            users.addUser(userName, true);
            object2FileJason("users.jason", users);
            //out.println(Config.USER_CREATED_AND_LOGGED);
            return Config.USER_CREATED_AND_LOGGED;
        }
    }

    private synchronized  void object2FileJason(String fileName, Object ob)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = "\\src\\main\\java\\it\\polimi\\ingsw\\storage\\";
        Gson gson = new Gson();
        try(FileWriter writer = new FileWriter(absolutePathToProject+pathFromContentRoot+fileName))
        {
            gson.toJson(ob, writer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private synchronized Object fileJason2Object(String fileName, Class ob)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = "\\src\\main\\java\\it\\polimi\\ingsw\\storage\\";
        Gson gson = new Gson();
        try (Reader reader = new FileReader(absolutePathToProject+pathFromContentRoot+fileName)) {

            // Convert JSON File to Java Object
            Object obb = gson.fromJson(reader, ob);
            return obb;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public synchronized Object fileBin2Object(String fileName)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = "\\src\\main\\java\\it\\polimi\\ingsw\\storage\\";
        try
        {
            FileInputStream fi = new FileInputStream(new File(absolutePathToProject+pathFromContentRoot+fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);
            return oi.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void Object2fileBin(String fileName, Object ob)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = "\\src\\main\\java\\it\\polimi\\ingsw\\storage\\";
        try
        {
            FileOutputStream f = new FileOutputStream(new File(absolutePathToProject+pathFromContentRoot+fileName));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(ob);

            o.close();
            f.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
    }

    private Game findGameForPlayer(String name)throws EriantysExceptions
    {
        for(Game game : games)
            for(Player player : game.getPlayers())
                if(player.getName().equals(name))
                    return game;
        throw new InnerExceptions.NoSuchUserException("This player is not in any game.");
    }

    private Player findPlayerByName(String name)throws EriantysExceptions
    {
        for(Game game : games)
            for(Player player : game.getPlayers())
                if(player.getName().equals(name))
                    return player;
        throw new InnerExceptions.NoSuchUserException("This player is not in any game.");
    }

}
