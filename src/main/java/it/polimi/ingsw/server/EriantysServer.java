package it.polimi.ingsw.server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;

import it.polimi.ingsw.client.User;
import it.polimi.ingsw.client.Users;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

import it.polimi.ingsw.model.*;


public class EriantysServer {
    /**
     * TODO YAN MANAGER AFK (AWAY FROM KEYBOARD)
     * IDEA: For updateReceiver start due thread
     *       1 for receive update from game server,
     *       throws timeOutException after 60s
     *       because other player is in AFK
     *
     *       2 for take ping signal from game server
     *       to prove client is still listening
     *       game server can throw IOException when failing connection with client
     *       means one player is offline so game server has to notify other players and
     *       then close the game
     * */

    /**
     * TODO YAN AUTO SAVE GAME
     * IDEA: Every time that the game has been modified, save the whole game to file
     *       then send the newest game to each player
     *
     *       steps:
     *       1. file -> object
     *       2. find previous version of this game
     *       3. replace it with newest version
     *       4. object -> file
     *       5. notify player
     * */
    static ArrayList<Game> games = new ArrayList<>();
    static ArrayList<Game> oldGames = new ArrayList<>();
    static ArrayList<Subscriber> subs = new ArrayList<>();
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try
        {
            onServerClose();
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


            while(true)
            {
                // waiting for a client to connect1
                client = server.accept();
                System.out.println("Client connected: "+client);
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


}
