package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) throws UnknownHostException {

        /**
        InetAddress iAddress = InetAddress.getLocalHost();
        String IP = iAddress.getHostAddress();
        System.out.println(IP);
         */
        ArrayList<Object> messages = new ArrayList<>();
        messages.add("Test_all_game_status");
       // ArrayList<Object> responses = responseFromServer(messages);
        ArrayList<Game> games = (ArrayList<Game>) fileBin2Object("gameRecord.bin");
        System.out.println(games.toString());

    }


    public static Object fileBin2Object(String fileName)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = Config.PATH_FROM_CONTENT_ROOT;
        try
        {
            FileInputStream fi = new FileInputStream(new File(pathFromContentRoot+fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);
            return oi.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    private static ArrayList<Object>  responseFromServer(ArrayList<Object> messages)
    {
        ArrayList<Object> responses = new ArrayList<>();
        try
        {
            Socket client = new Socket("localhost", 12345);
            // Input stream
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            oos.writeObject(messages);
            responses = (ArrayList<Object>) ois.readObject();
            client.close();
            return responses;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        responses.add("Unknown Error");
        return responses;
    }
}

