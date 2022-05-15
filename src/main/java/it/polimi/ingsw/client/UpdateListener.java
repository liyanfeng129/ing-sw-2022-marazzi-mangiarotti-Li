package it.polimi.ingsw.client;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class UpdateListener extends Thread{
    private EriantysCLIClientThread cliClient;
    private String serverAddress;
    private String userName;
    public UpdateListener( EriantysCLIClientThread cliClient, String serverAddress, String userName)
    {
        this.cliClient = cliClient;
        this.serverAddress = serverAddress;
        this.userName = userName;
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
            ArrayList<Object> responses = (ArrayList<Object>) ois.readObject();
            update(responses);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new UpdateListener(cliClient, serverAddress, userName).start();
        }
    }
    public synchronized void update(ArrayList<Object> messages) throws EriantysExceptions {
       // clearScreen();
        String msg = (String) messages.get(0);
        ArrayList<Object> newMessages = new ArrayList<>();
        ArrayList<Object> responses;
        switch (msg)
        {
            case Config.UPDATE_CREATOR_WAITING_ROOM :
                updateCreatorGameRoom((Game) messages.get(1));
                break;
            case Config.UPDATE_OTHER_WAITING_ROOM:
                updateOtherPlayerGameRoom((Game) messages.get(1));
                break;
            case Config.GAME_UPDATED:
                newMessages.add(Config.GET_NEWEST_GAME);
                newMessages.add(userName);
                responses = responseFromServer(newMessages);
                if(responses.get(0).equals(Config.GET_NEWEST_GAME_SUC));
                gameUpdate((Game) responses.get(1));
                break;

        }
    }
    private synchronized void updateCreatorGameRoom(Game game) {
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
    private  void gameUpdate(Game game) throws EriantysExceptions {
        System.out.println("Game has been updated");
        new Cli().show_game(game);
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
        else
        {
            // communicate
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
}
