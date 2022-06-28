package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GuiListener extends Thread{
    private AASceneParent caller;
    private int portNumber;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket update;
    private String userName;
    private String serverAddress;
    private boolean receiverOn;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Thread pingServer;
    public GuiListener(int portNumber,String userName,String serverAddress)
    {
        this.portNumber = portNumber;
        this.userName = userName;
        this.serverAddress =serverAddress;
        this.receiverOn = true;
        pingServer = new Thread(){
            boolean threadOn = true;
            @Override
            public void run() {
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
                            receiverOn = false;
                            update.close();
                            threadOn = false;
                        }
                    }
                } catch (Exception e) {
                    if(e instanceof SocketException)
                    {
                        System.out.println("Something went wrong with server");
                        receiverOn = false;
                    }
                    else if(e instanceof ConnectException)
                    {
                        System.out.println("Cannot create connection with server");
                        receiverOn = false;
                    }
                    else
                        e.printStackTrace();
                }
            }
            @Override
            public void interrupt() {
                threadOn = false;
            }
        };
    }

    public void run()
    {
        try {
            ServerSocket updateReceiver = new ServerSocket(portNumber);
            pingServer.start();
            while(receiverOn)
            {
                update = updateReceiver.accept();
                System.out.println(caller.getInfo().getUserName() + "listening");
                oos = new ObjectOutputStream(update.getOutputStream());
                ois=new ObjectInputStream(update.getInputStream());
                ArrayList<Object> updates = (ArrayList<Object>) ois.readObject();
                update.close();
                if(updates.get(0).equals(Config.GAME_OVER))
                {
                    String motivation = (String) updates.get(1);
                    receiverOn = false;
                    System.out.println(motivation);
                }
                else
                    caller.listenerCallBack(updates);
            }
            updateReceiver.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    public AASceneParent getCaller() {
        return caller;
    }

    public synchronized void setCaller(AASceneParent caller) {
        this.caller = caller;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public Socket getUpdate() {
        return update;
    }

    public void setUpdate(Socket update) {
        this.update = update;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public boolean isReceiverOn() {
        return receiverOn;
    }

    public void setReceiverOn(boolean receiverOn) {
        this.receiverOn = receiverOn;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String toString() {
        return "GuiListener{" +
                "caller=" + caller +
                ", portNumber=" + portNumber +
                ", oos=" + oos +
                ", ois=" + ois +
                ", update=" + update +
                ", userName='" + userName + '\'' +
                ", serverAddress='" + serverAddress + '\'' +
                ", receiverOn=" + receiverOn +
                ", dateFormat=" + dateFormat +
                '}';
    }
}
