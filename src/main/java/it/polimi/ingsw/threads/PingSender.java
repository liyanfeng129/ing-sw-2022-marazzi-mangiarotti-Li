package it.polimi.ingsw.threads;

import it.polimi.ingsw.GUI.AASceneParent;
import it.polimi.ingsw.model.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class PingSender extends Thread{
    private AASceneParent caller;
    private String serverAddress;
    private String userName;
    private boolean threadOn;

    public PingSender(AASceneParent caller, String serverAddress, String userName) {
        this.caller = caller;
        this.serverAddress = serverAddress;
        this.userName = userName;
        threadOn = true;
    }

    public void run()
    {
        System.out.println("PingSender on");
        try
        {
            while (threadOn)
            {
                sleep(5000);
                ArrayList<Object> messages = new ArrayList<>();
                messages.add(Config.CLIENT_PING_SERVER);
                messages.add(userName);
                ArrayList<Object> responses = responseFromServer(messages);
                if(!responses.get(0).equals(Config.SERVER_IS_ON))
                {
                    threadOn = false;
                }
            }
        } catch (Exception e) {
           caller.errorCommunicate(e);
        }
    }

    private  ArrayList<Object> responseFromServer(ArrayList<Object> messages) throws IOException, ClassNotFoundException {
        ArrayList<Object> responses = new ArrayList<>();

        Socket client = new Socket(serverAddress, 12345);
        // Input stream
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        oos.writeObject(messages);
        responses = (ArrayList<Object>) ois.readObject();
        client.close();
        return responses;

    }

}
