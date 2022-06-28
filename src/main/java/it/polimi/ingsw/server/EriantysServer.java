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
    static int AFKTimeOut = 60;
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
                        sleep(5000);
                        afkCheck();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(()->{
                while (true)
                {
                    try {
                        sleep(5000);
                        checkSubs();
                    } catch (InterruptedException e) {
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

    private static void checkSubs() throws InterruptedException {
        for(Subscriber sub : subs)
            if(timeDiff(sub.getLastAccessTime(),LocalDateTime.now())>pingNotResponseTimeOut) //
            {
                new Thread(()->{
                    try {
                        logOutUser(sub.getUserName());
                    } catch (EriantysExceptions e) {
                        e.printStackTrace();
                    }
                }).start();
            }
    }

    /**
     * Whenever a game has been updated, the local time variable inside game will be renewed
     * so if a game has been to loong without modification means some player is away from keyboard
     * */
    private static void afkCheck() throws InterruptedException {
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
                            notifySubs_GameOver(g,"Game is over because someone is in AFK.");
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

    public static int timeDiff(LocalDateTime t1, LocalDateTime t2) throws InterruptedException {
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

    private static void Object2fileBin(String fileName, Object ob)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = Config.PATH_FROM_CONTENT_ROOT;
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
    private static Object fileBin2Object(String fileName)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = Config.PATH_FROM_CONTENT_ROOT;
        try
        {
            FileInputStream fi = new FileInputStream(new File(absolutePathToProject+pathFromContentRoot+fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);
            return oi.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }


    private static void onServerClose() throws Exception{
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                System.out.println("Server on close.");
                saveGames();
                try
                {
                    for(Subscriber sub : subs)
                    {
                        System.out.println(String.format("closing %s's connection\n " +
                                "ip address: %s      Port number: %d",sub.getUserName(), sub.getIpAddress(), sub.getPortNumber()));
                        Socket notify = new Socket(sub.getIpAddress(),sub.getPortNumber());
                        ObjectOutputStream oos = new ObjectOutputStream(notify.getOutputStream());
                        ArrayList<Object> msg = new ArrayList<>();
                        msg.add(Config.SERVER_CLOSE);
                        oos.writeObject(msg);
                    }
                    logOutAll();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * get gameRecord and turn it into an object then ad new games to it and then save to file
     * */
    private static void saveGames()
    {
        ArrayList<Game> oldGames = null;
        oldGames = (ArrayList<Game>) fileBin2Object("gameRecord.bin");
        if(oldGames == null)
            oldGames = new ArrayList<>();
        for(Game g : games)
            if(g.isGameStarted())
                oldGames.add(g);
        Object2fileBin("gameRecord.bin",oldGames);
    }

    private static void notifySubs_GameOver(Game game, String message) {
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
                                Socket notify = new Socket(sub.getIpAddress(),sub.getPortNumber());
                                ObjectOutputStream oos = new ObjectOutputStream(notify.getOutputStream());
                                ArrayList<Object> msg = new ArrayList<>();
                                msg.add(Config.GAME_OVER);
                                msg.add(message);
                                oos.writeObject(msg);
                            }
                            catch (Exception e)
                            {
                                if(e instanceof ConnectException)
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
        Users userList = (Users) fileJason2Object("users.json", Users.class);
        userList.logOutUser(userName);
        object2FileJason("users.json", userList);
        try
        {
            Game g = findGameForPlayer(userName);
            notifySubs_GameOver(g, "Game is over, because one player is out of reach");
            games.remove(g);
        }
        catch (InnerExceptions.NoSuchUserException e)
        {
            System.out.println(String.format("%s has no game associated logout success",userName));
            for(Iterator<Subscriber> ite = subs.iterator(); ite.hasNext();) {
                Subscriber sub = ite.next();
                if (sub.getUserName().equals(userName))
                    ite.remove();
            }
        }
    }
}
