package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.client.Users;
import it.polimi.ingsw.model.*;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Thread.sleep;


public class EriantysServer {
    static int AFKTimeOut = 6000;
    static int pingNotResponseTimeOut = 10;
    static ArrayList<Game> games = new ArrayList<>();
    static ArrayList<Game> oldGames = new ArrayList<>();
    static ArrayList<Subscriber> subs = new ArrayList<>();
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try
        {
            //onServerClose();
            //Create socket server
            ServerSocket server = new ServerSocket(12345);

            InetAddress iAddress = InetAddress.getLocalHost();
            String server_IP = iAddress.getHostAddress();
            System.out.println("Server IP address : " +server_IP);
            logOutAll();

            // declare socket client
            Socket client;

            String message;

            System.out.println("Server ready");


            new Thread(()->{
                while (true)
                {
                    try {
                        sleep(10*1000);
                        afkCheck();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(()->{
                while (true)
                {
                    try {
                        sleep(10*1000);
                        checkSubs();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while(true)
            {
                // waiting for a client to connect1
                client = server.accept();
               // System.out.println("Client connected: "+client);
                new EriantysClientHandler(client,games,subs,oldGames).start();
            }

        }
        catch (Exception e)
        {
            logOutAll();
            e.printStackTrace();
        }
    }

    private static void logOutAll()
    {
        Users userList = (Users) fileJason2Object("users.json", Users.class);
        userList.logOutAll();
        object2FileJason("users.json", userList);
    }

    private static void checkSubs() throws Exception {
        ArrayList<Integer> subsIndexToDelete = new ArrayList<>();
        synchronized (subs)
        {
            for(Subscriber sub : subs)
                if(timeDiff(sub.getLastAccessTime(),LocalDateTime.now()) > pingNotResponseTimeOut) //
                {
                    System.out.println(sub.getUserName()+" is out of reach, check sub failed");
                    subsIndexToDelete.add(subs.indexOf(sub));
                    new Thread(()->{
                        try {
                            logOutUser(sub.getUserName());
                            Game g = findGameForPlayer(sub.getUserName());
                            if(g!=null)
                            {
                                notifySubs_GameOver(g,Config.GAME_OVER_CAUSE_ONE_IS_OFFLINE);
                            }
                        } catch (EriantysExceptions e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
        }
        synchronized (subs)
        {
            for(int i : subsIndexToDelete)
                subs.remove(i);
        }
    }

    /**
     * Whenever a game has been updated, the local time variable inside game will be renewed
     * so if a game has been to loong without modification means some player is away from keyboard
     * */
    private static void afkCheck() throws Exception {
        ArrayList<Integer> gamesIndexToDelete = new ArrayList<>();
        for(Game g : games)
        {
            synchronized (g)
            {
                /*
                 * if game is started and for too long time there hasn't been a modification
                 *
                 */
                if (g.isGameStarted())
                {
                    if(timeDiff(g.getLastAccessTime(), LocalDateTime.now()) > AFKTimeOut)
                    {
                        gamesIndexToDelete.add(games.indexOf(g));
                        new Thread(() -> {
                            notifySubs_GameOver(g,Config.GAME_OVER_CAUSE_AFK);
                        }).start();
                    }
                }

            }
        }

        if(gamesIndexToDelete.size() > 0)
        {
            synchronized (games)
            {
                for(int i : gamesIndexToDelete)
                    games.remove(i);
            }
        }
    }

    public static int timeDiff(LocalDateTime t1, LocalDateTime t2) throws Exception {
        return (int) ChronoUnit.SECONDS.between(t1, t2);
    }


    private static synchronized Object fileJason2Object(String fileName, Class ob)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = Config.PATH_FROM_CONTENT_ROOT;
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
    private static synchronized  void object2FileJason(String fileName, Object ob)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = Config.PATH_FROM_CONTENT_ROOT;
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


    private static void notifySubs_GameOver(Game game, String message) {
        System.out.println("closing game of "+game.getPlayers().get(0).getName());
        synchronized (games)
        {
            for(Player p : game.getPlayers())
                synchronized (subs)
                {
                    for(Iterator<Subscriber> ite = subs.iterator(); ite.hasNext();)
                    {
                        Subscriber sub = ite.next();
                        if(sub.getUserName().equals(p.getName()))
                        {
                            new Thread(()->{
                                try {
                                    String ip;
                                    int port;
                                    synchronized (sub)
                                    {
                                        ip = sub.getIpAddress();
                                        port = sub.getPortNumber();
                                    }
                                    Socket notify = new Socket(ip,port);
                                    ObjectOutputStream oos = new ObjectOutputStream(notify.getOutputStream());
                                    ArrayList<Object> msg = new ArrayList<>();
                                    msg.add(Config.GAME_OVER);
                                    msg.add(message);
                                    oos.writeObject(msg);
                                }
                                catch (Exception e)
                                {
                                    if(e instanceof ConnectException) // client is down
                                    {
                                        System.out.println(sub.getUserName()+" is out of reach");

                                    }
                                    else
                                        e.printStackTrace();
                                }
                            }).start();
                        }
                    }

                }
            games.remove(game);
        }
    }
    private static Game findGameForPlayer(String name)throws EriantysExceptions
    {
        for(Game game : games)
            for(Player player : game.getPlayers())
                if(player.getName().equals(name))
                    return game;
        throw new InnerExceptions.NoSuchUserException("This player is not in any game.");
    }

    private static void logOutUser(String userName) throws EriantysExceptions {
        System.out.println("logout user: "+userName);
        Users userList = (Users) fileJason2Object("users.json", Users.class);
        userList.logOutUser(userName);
        object2FileJason("users.json", userList);
    }
}
