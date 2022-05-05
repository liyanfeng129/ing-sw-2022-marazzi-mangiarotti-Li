package it.polimi.ingsw.client;

import it.polimi.ingsw.Config;
import it.polimi.ingsw.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UpdateListener extends Thread{
    private EriantysCLIClient cliClient;
    private String serverAddress;
    private test2 test;
    public UpdateListener(EriantysCLIClient cliClient, String serverAddress)
    {
        this.cliClient = cliClient;
        this.serverAddress = serverAddress;
    }

    public UpdateListener(test2 test, String serverAddress)
    {
        this.test = test;
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
            messages.add(test.player);
            while (true)
            {
                ArrayList<Object> responses = (ArrayList<Object>) ois.readObject();
                String msg = (String) responses.get(0);
                if(msg.equals(Config.GAME_UPDATED))
                    test.updateGame((Game) responses.get(1));

                   // cliClient.updateGame((Game) responses.get(1));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
