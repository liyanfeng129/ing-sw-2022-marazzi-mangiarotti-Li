package it.polimi.ingsw.threads;

import it.polimi.ingsw.client.UpdateReceiver;
import it.polimi.ingsw.model.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class AFKTrigger extends Thread{
    private String serverAddress;
    private boolean threadOn;
    private int count;
    private int timeOut;
    private String username;
    private Object lock = new Object();
    private UpdateReceiver ur;
    public AFKTrigger(int timeOut, String serverAddress, String username, UpdateReceiver ur)
    {
        threadOn = true;
        count = 0;
        this.timeOut = timeOut;
        this.serverAddress = serverAddress;
        this.username = username;
        this.ur = ur;
    }
    public void run()
    {
        try {
            while (threadOn)
            {
                sleep(1000);
                synchronized (lock)
                {
                    count ++;
                    if(count > timeOut)
                    {
                        System.out.println( String.format("count = %d : timeout = %d, please put some input to continue",count,timeOut));
                        ArrayList<Object> messages = new ArrayList<>();
                        messages.add(Config.CLIENT_AFK_NOTIFYING);
                        messages.add(username);
                        ArrayList<Object> responses = responseFromServer(messages);
                        threadOn = false;
                        ur.setReceiverOn(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void interrupt()
    {
        threadOn = false;
    }

    public boolean isInterrupt()
    {
        return threadOn;
    }

    public void reset()
    {
        synchronized (lock)
        {
            if(!threadOn)
                throw new RuntimeException("AKFTriggered");
            count = 0;
        }
    }
}
