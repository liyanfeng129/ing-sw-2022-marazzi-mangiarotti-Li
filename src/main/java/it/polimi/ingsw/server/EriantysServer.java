package it.polimi.ingsw.server;
import com.google.gson.Gson;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.client.User;
import it.polimi.ingsw.client.Users;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EriantysServer {
    static ArrayList<Game> games = new ArrayList<>();
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try
        {
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
                // waiting for a client to connect
                client = server.accept();
                System.out.println("Client connected: "+client);
                new EriantysClientHandler(client,games).start();
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
        Users userList = (Users) fileJason2Object("users.jason", Users.class);
        userList.logOutAll();
       object2FileJason("users.jason", userList);
    }

    private static synchronized Object fileJason2Object(String fileName, Class ob)
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
    private static synchronized  void object2FileJason(String fileName, Object ob)
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


}
