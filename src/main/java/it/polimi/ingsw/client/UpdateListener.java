package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UpdateListener extends Thread{
    private EriantysCLIClientThread cliClient;
    private String serverAddress;
    public UpdateListener( EriantysCLIClientThread cliClient, String serverAddress)
    {
        this.cliClient = cliClient;
        this.serverAddress = serverAddress;
    }

    public void run()
    {
        Socket client = null;
        try
        {
            client = new Socket(serverAddress, 12345);
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            ArrayList<Object> messages = new ArrayList<>();
            messages.add(Config.LISTENING_FOR_UPDATE);
            messages.add(cliClient.getUserName());
            oos.writeObject(messages);
            while (true)
            {
                ArrayList<Object> responses = (ArrayList<Object>) ois.readObject();
                String msg = (String) responses.get(0);
                if(msg.equals(Config.GAME_UPDATED))
                    cliClient.updateGame((Game) responses.get(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }

}
