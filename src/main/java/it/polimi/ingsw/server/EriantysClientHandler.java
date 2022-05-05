package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.*;
import it.polimi.ingsw.client.User;
import it.polimi.ingsw.client.Users;
import it.polimi.ingsw.model.*;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class EriantysClientHandler extends Thread{
    BufferedReader in;
    PrintWriter out;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Socket client;
    ArrayList<Game> games;
    Player player;
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
            switch (request)
            {
                case Config.CREATE_GAME_FOR_2:
                    System.out.println(client+"tries to create a game for two");
                    userName = (String) messages.get(1);
                    msg = createGameFor2(userName);
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
                    msg = joinAGame(gameRoom, playerName);
                    game = findGameForPlayer(playerName);
                    responses.add(msg);
                    responses.add(game);
                    oos.writeObject(responses);
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
                case Config.LOG_OUT:
                    System.out.println(client+" asking for logout");
                    responses.add(logOutUser((String) messages.get(1)));
                    oos.writeObject(responses);
                    break;
                case "test":
                    System.out.println(client+" is doing a test");
                    int ob1 = (int) messages.get(1);
                    Bag ob2 = (Bag) messages.get(2);
                    System.out.println(ob1);
                    System.out.println(ob2);
                    responses.add("test successful");
                    responses.add(new User("nima", false));
                    responses.add(new Island());
                    oos.writeObject(responses);
                    break;
                case "testModifyPlayer":
                    System.out.println(client+" tries to modify game");
                    game = findGameForPlayer((String) messages.get(1));
                    for(Player p : game.getPlayers())
                        p.unLockUpdate();
                    responses.add("all player has been notified");
                    oos.writeObject(responses);


                case Config.LISTENING_FOR_UPDATE:
                    System.out.println(client+" is listening for update");
                    player = findPlayerByName((String) messages.get(1));
                    while(!client.isClosed())
                    {
                        player.lockUpdate();
                        responses = new ArrayList<>();
                        responses.add(Config.GAME_UPDATED);
                        responses.add(findGameForPlayer(player.getName()));
                        oos.writeObject(responses);
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

    }
    /**
     * TODO
     *
     * */
    private synchronized String joinAGame(String gameName,String player) throws EriantysExceptions {
        Game game = findGameForPlayer(gameName);
        if(game.isGameStarted())
            throw new InnerExceptions.GameStartingError("Cannot join a started game");
        if(game.getPlayers().size() == game.getN_Player())
            throw new InnerExceptions.GameStartingError("Cannot join a game where is full");
        if(game.getN_Player() == 2 && !game.isExpertMode())
        {
            game.addPlayers(new Player(player,Mage.MAGE2,TowerColor.WHITE,2,true));
        }
            return Config.JOIN_ONE_GAME_SUC;
    }

    private synchronized String createGameFor2(String userName) throws EriantysExceptions {
        Game game = new Game(2, false, new Player(userName,Mage.MAGE1,TowerColor.BLACK,2,true));
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
