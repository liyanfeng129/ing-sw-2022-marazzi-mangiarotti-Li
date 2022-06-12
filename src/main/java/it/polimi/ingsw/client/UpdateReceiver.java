package it.polimi.ingsw.client;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.Cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UpdateReceiver extends Thread {

    private int portNumber;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket update;
    private String userName;
    private String serverAddress;
    private boolean receiverOn;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public UpdateReceiver(int portNumber,String userName,String serverAddress)
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
                    updateReceived(updates);
            }
            updateReceiver.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    public void updateReceived(ArrayList<Object> messages) throws EriantysExceptions {
         clearScreen();
        String msg = (String) messages.get(0);
        System.out.println(dateFormat.format(new Date()));
        switch (msg)
        {
            case Config.UPDATE_CREATOR_WAITING_ROOM :
                updateCreatorGameRoom((Game) messages.get(1));
                break;
            case Config.UPDATE_OTHER_WAITING_ROOM:
                updateOtherPlayerGameRoom((Game) messages.get(1));
                break;
            case Config.UPDATE_CREATOR_WAITING_ROOM_FOR_OLD_GAME:
                updateCreatorOldGameRoom((Game) messages.get(1));
                break;
            case Config.UPDATE_OTHER_WAITING_ROOM_FOR_OLD_GAME:
                updateOtherPlayerOldGameRoom((Game) messages.get(1));
                break;
            case Config.GAME_UPDATED:
               gameUpdate((Game) messages.get(1));
                break;
        }
    }
    private void updateCreatorOldGameRoom(Game game)
    {
        clearScreen();
        System.out.println(String.format("%s's %s game for %d, started in %s with player: %s, %s .",
                game.getTurnList().get(0).getName(),
                (game.isExpertMode()? "expert" : "normal"),
                game.getN_Player(),
                game.getGameStartingTime(),
                (game.getN_Player() > 1 ? game.getTurnList().get(1).getName() : " " ),
                (game.getN_Player() > 2 ? game.getTurnList().get(2).getName() : " " )
        ));
        System.out.println("Participants:\n");
        int i = 0;
        for (; i < game.getPlayers().size(); i++)
            System.out.println(String.format("%d: %s",
                    i + 1,
                    game.getPlayers().get(i).getName()
            ));
        if (game.getN_Player() == game.getPlayers().size())
        {
            String input = "";
            while(!input.equals("1"))
            {
                System.out.println("Game is ready to be restarted, press 1 if you want to start\n" +
                        "every other input is invalid\n");
                input = new Scanner(System.in).nextLine();
                if(input.equals("1"))
                {
                    ArrayList<Object> messages = new ArrayList<>();
                    messages.add(Config.GAME_OLD_START);
                    messages.add(userName);
                    ArrayList<Object> responses = responseFromServer(messages);
                    String msg = (String) responses.get(0);
                    if(msg.equals(Config.GAME_OLD_START_SUC))
                    {
                        System.out.println("Launching..");
                    }
                }
            }
        }
    }

    private void updateOtherPlayerOldGameRoom(Game game)
    {
        clearScreen();
        System.out.println(String.format("%s's %s game for %d, started in %s with player: %s, %s .",
                game.getTurnList().get(0).getName(),
                (game.isExpertMode()? "expert" : "normal"),
                game.getN_Player(),
                game.getGameStartingTime(),
                (game.getN_Player() > 1 ? game.getTurnList().get(1).getName() : " " ),
                (game.getN_Player() > 2 ? game.getTurnList().get(2).getName() : " " )
        ));
        System.out.println("Participants:\n");
        int i = 0;
        for (; i < game.getPlayers().size(); i++)
            System.out.println(String.format("%d: %s",
                    i + 1,
                    game.getPlayers().get(i).getName()
            ));
        System.out.println("Please wait for game to be started");
    }

    private void updateCreatorGameRoom(Game game) {
        System.out.println(String.format("%s, this is your game, waiting for other %d\n",
                game.getPlayers().get(0).getName(),
                game.getN_Player() - game.getPlayers().size()
        ));
        System.out.println("Participants:\n");
        int i = 0;
        for (; i < game.getPlayers().size(); i++)
            System.out.println(String.format("%d: %s",
                    i + 1,
                    game.getPlayers().get(i).getName()
            ));
        if (game.getN_Player() == game.getPlayers().size())
        {
            String input = "";
            while(!input.equals("1"))
            {
                System.out.println("Game is ready to be started, press 1 if you want to start\n" +
                        "every other input is invalid\n");
                input = new Scanner(System.in).nextLine();
                if(input.equals("1"))
                {
                    ArrayList<Object> messages = new ArrayList<>();
                    messages.add(Config.GAME_START);
                    messages.add(userName);
                    ArrayList<Object> responses = responseFromServer(messages);
                    String msg = (String) responses.get(0);
                    if(msg.equals(Config.GAME_START_SUC))
                    {
                        System.out.println("Launching..");
                    }
                }
            }
        }
    }
    private  void updateOtherPlayerGameRoom(Game game)
    {
        System.out.println(String.format("you are in %s's game, waiting for other %d\n",
                game.getPlayers().get(0).getName(),
                game.getN_Player()-game.getPlayers().size()
        ));
        System.out.println("Participants:\n");
        int i = 0;
        for(;i<game.getPlayers().size();i++)
            System.out.println(String.format("%d: %s",
                    i+1,
                    game.getPlayers().get(i).getName()
            ));
    }
    private void gameUpdate(Game game) throws EriantysExceptions {
        System.out.println("Game has been updated");
        new Cli().show_game(game);
        Command c = game.getExecutedCommand();
        if(c.isDataGathered())
            System.out.println("\n"+c.getMsg()+"\n");
        if(game.getLastCommand().getUsername().equals(userName))
        {
            Command command = game.getLastCommand();
            command.getData();
            ArrayList<Object> messages = new ArrayList<>();
            ArrayList<Object> responses = new ArrayList<>();
            messages.add(Config.COMMAND_EXECUTE);
            messages.add(userName);
            messages.add(command);
            responses = responseFromServer(messages);
            if(responses.get(0).equals(Config.COMMAND_EXECUTE_SUC))
                System.out.println("Command executed");
            else
                System.out.println(responses.get(0));
        }
        else if (game.getLastCommand().getUsername().equals("endgame")){
            Command command = game.getLastCommand();
            command.getData();
        }
    }
    private  void clearScreen()
    {
        for(int clear = 0; clear < 50; clear++)
        {
            System.out.println("\b") ;
        }
    }
    private  ArrayList<Object> responseFromServer(ArrayList<Object> messages)
    {
        ArrayList<Object> responses = new ArrayList<>();
        try
        {
            Socket client = new Socket(serverAddress, 12345);
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

    public boolean isReceiverOn() {
        return receiverOn;
    }

    public synchronized void setReceiverOn(boolean receiverOn) {
        this.receiverOn = receiverOn;
    }
}
