package it.polimi.ingsw.client;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.Cli;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
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
    private EriantysCLIClientThread EriantysClient;
    private ServerSocket updateReceiver;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Thread pingServer;
    public UpdateReceiver(int portNumber,String userName,String serverAddress, EriantysCLIClientThread eriantysClient)
    {
        this.portNumber = portNumber;
        this.userName = userName;
        this.serverAddress =serverAddress;
        this.receiverOn = true;
        this.EriantysClient = eriantysClient;

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
                    else if(e instanceof ConnectException || e instanceof EOFException)
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
        pingServer.start();
        try {
            updateReceiver = new ServerSocket(portNumber);
            while(receiverOn)
            {
                update = updateReceiver.accept();
                oos = new ObjectOutputStream(update.getOutputStream());
                ois=new ObjectInputStream(update.getInputStream());
                ArrayList<Object> updates = (ArrayList<Object>) ois.readObject();
                update.close();
                if(updates.get(0).equals(Config.GAME_OVER))
                {
                    String motivation = (String) updates.get(1);
                    receiverOn = false;
                    System.out.println(motivation);
                    EriantysClient.clone().start();

                }
                else if(updates.get(0).equals(Config.SERVER_CLOSE))
                {
                    receiverOn = false;
                    System.out.println("Server shut down.");
                    new EriantysCLIClientThread().start();
                }
                else
                {
                    new Thread(() -> {
                        try {
                            updateReceived(updates);
                        } catch (EriantysExceptions e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }

            }
            updateReceiver.close();

        } catch (Exception e) {
            if(e instanceof SocketException)
            {
                System.out.println("Something went wrong with server");
                EriantysClient.clone().start();
            }
            else if(e instanceof SocketTimeoutException)
            {
                System.out.println("One player is in AFK, return to menu page.\n" +
                        "you can reload this game in the future.");
                EriantysClient.clone().start();
            }
            else if(e instanceof RuntimeException)
            {
                e.printStackTrace();
                System.out.println("AKF triggered");
                EriantysClient.clone().start();
            }
            else
                e.printStackTrace();
        }
        finally {
            if(!updateReceiver.isClosed()) {
                try {
                    updateReceiver.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateReceived(ArrayList<Object> messages) throws EriantysExceptions, IOException, ClassNotFoundException {
        clearScreen();
        String msg = (String) messages.get(0);
        Game g = (Game) messages.get(1);
        System.out.println(dateFormat.format(new Date()));
        switch (msg)
        {
            case Config.UPDATE_CREATOR_WAITING_ROOM :
                updateCreatorGameRoom(g);
                break;
            case Config.UPDATE_OTHER_WAITING_ROOM:
                updateOtherPlayerGameRoom(g);
                break;
            case Config.UPDATE_CREATOR_WAITING_ROOM_FOR_OLD_GAME:
                updateCreatorOldGameRoom(g);
                break;
            case Config.UPDATE_OTHER_WAITING_ROOM_FOR_OLD_GAME:
                updateOtherPlayerOldGameRoom(g);
                break;
            case Config.GAME_UPDATED:
                /**
                 * if within 60s receiver don't get update, someone is in afk
                 * close receiver

                if(g.getPlayers().get(0).getName().equals(userName))
                {
                    if(akfTrigger.isAlive() ) {
                        akfTrigger.reset();// reset akf time
                    } else
                        akfTrigger.start();
                }
                 * */
                gameUpdate(g);
                break;
        }
    }
    private void updateCreatorOldGameRoom(Game game) throws IOException, ClassNotFoundException {
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
                    messages.add(game.getGameStartingTime()); // using game starting time to identify uniquely a game
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

    private void updateCreatorGameRoom(Game game) throws IOException, ClassNotFoundException {
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
    private void gameUpdate(Game game) throws EriantysExceptions, IOException, ClassNotFoundException {
        System.out.println("Game has been updated");
        new Cli().show_game(game);
        Command c = game.getExecutedCommand();
        if(c.isDataGathered())
            System.out.println("\n"+c.getMsg()+"\n");
        if(game.getLastCommand().getUsername().equals(userName))
        {
            Command command = game.getLastCommand();
            command.getData();
            if(receiverOn) // false -> because you are in AFK , user is stacked with getData
            {
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
                System.out.println("WHAT TOOK YOU SO LONG? Put some input to continue");
        }
        else if (game.getLastCommand().getUsername().equals("endgame")){
            Command command = game.getLastCommand();
            command.getData();
            System.out.println("Press 1 to start a new game");
            int choice = getInput() - 1;
            /**
             * TODO yanfeng
             */
            updateReceiver.close();
        }
    }
    private  void clearScreen()
    {
        for(int clear = 0; clear < 50; clear++)
        {
            System.out.println("\b") ;
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

    public boolean isReceiverOn() {
        return receiverOn;
    }

    public synchronized void setReceiverOn(boolean receiverOn) {
        this.receiverOn = receiverOn;
    }
    public int getInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = input.replaceAll("\\D+","");
        while (input.length()==0){
            System.out.println("Pleas input an integer");
            input = scanner.nextLine();
            input = input.replaceAll("\\D+","");
        }
        return Integer.parseInt(input);
    }

}