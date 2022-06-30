package it.polimi.ingsw.client;

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
        ArrayList<Object> responses = responseFromServer(messages);

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

