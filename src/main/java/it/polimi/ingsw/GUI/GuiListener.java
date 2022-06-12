package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
    public GuiListener(int portNumber,String userName,String serverAddress)
    {
        this.portNumber = portNumber;
        this.userName = userName;
        this.serverAddress =serverAddress;
        this.receiverOn = true;
    }

    public void run()
    {
        try {
            ServerSocket updateReceiver = new ServerSocket(portNumber);
            while(receiverOn)
            {
                update = updateReceiver.accept();
                System.out.println(caller.toString() + "listening");
                oos = new ObjectOutputStream(update.getOutputStream());
                ois=new ObjectInputStream(update.getInputStream());
                ArrayList<Object> updates = (ArrayList<Object>) ois.readObject();
                update.close();
                if(updates.get(0).equals(Config.GAME_OVER))
                {
                    receiverOn = false;
                    System.out.println("Game is closing.");
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

    public void updateReceived(ArrayList<Object> messages) throws EriantysExceptions {
        String msg = (String) messages.get(0);
        System.out.println(dateFormat.format(new Date()));
        switch (msg)
        {
            case Config.UPDATE_CREATOR_WAITING_ROOM :
                //updateCreatorGameRoom((Game) messages.get(1));
                break;
            case Config.UPDATE_OTHER_WAITING_ROOM:
                //updateOtherPlayerGameRoom((Game) messages.get(1));
                break;
            case Config.UPDATE_CREATOR_WAITING_ROOM_FOR_OLD_GAME:
                //updateCreatorOldGameRoom((Game) messages.get(1));
                break;
            case Config.UPDATE_OTHER_WAITING_ROOM_FOR_OLD_GAME:
               // updateOtherPlayerOldGameRoom((Game) messages.get(1));
                break;
            case Config.GAME_UPDATED:
               // gameUpdate((Game) messages.get(1));
                break;
        }
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
