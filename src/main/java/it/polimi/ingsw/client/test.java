package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.Methods;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;

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
        Methods methods = new Methods();

        //Users users = new Users();


    }


    public static Object fileBin2Object(String fileName)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = Config.PATH_FROM_CONTENT_ROOT;
        ObjectInputStream oi;

        try
        {
            FileInputStream fi = new FileInputStream(new File(pathFromContentRoot+fileName));
            oi = new ObjectInputStream(fi);
            return oi.readObject();
        }
        catch (Exception e)
        {
            try {
                var input = Objects.requireNonNull(test.class.getClassLoader().getResourceAsStream("it/polimi/ingsw/server/storage/"+fileName));
                oi = new ObjectInputStream(input);
                return oi.readObject();
            }catch (Exception ex){
                e.getStackTrace();
            }

        }
        return null;

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

