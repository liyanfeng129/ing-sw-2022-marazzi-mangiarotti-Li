package it.polimi.ingsw.client;


import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class EriantysCLIClientThread extends Thread {
    private  String serverAddress = "localhost";
    private  String userName = "";
    private  int listeningPortNumber = new Random().nextInt(65353);
    private  void loggingMenu() throws InterruptedException, UnknownHostException {
        clearScreen();
        String response;
        System.out.println("Do you want to log?\n"+"1.YES\n"+"Everything else you enter terminates program\n");
        response = new Scanner(System.in).nextLine();
        if(response.equals("1"))
        {
            do
            {
                response = loggingWithUserName();
                switch (response)
                {
                    case Config.USER_ALREADY_LOGGED:
                        System.out.println(userName + " has already logged, please use another user name.");
                        break;
                    case Config.USER_LOGGED:
                        System.out.println(userName + " is logged successfully." );
                        break;
                    case Config.USER_CREATED_AND_LOGGED:
                        System.out.println(userName + " is created and logged.");
                        break;
                    default:
                        System.out.println("Unknown response (error): "+ response);
                        break;
                }

            }
            while(response.equals(Config.USER_ALREADY_LOGGED));
            new UpdateReceiver(listeningPortNumber,userName,serverAddress).start();
            lobbyMenu();
        }
    }
    private void lobbyMenu() throws InterruptedException, UnknownHostException {
        /**
         * 1. Create a new game for 2 players
         * 2. Create a new game for 3 players
         * 3. Create a new game for 4 players
         * 4. Resume an old game
         * 5. Show existing games in the lobby
         * 6. Show resumable games
         * 7. Join in a resumable game
         * 8. logout and exit
         * */
        clearScreen();
        int option = -1;
        boolean exit;
        ArrayList<Object> responses = new ArrayList<>();
        do {
            messagePrinter("welcome.txt");
            option = new Scanner(System.in).nextInt();
            String msg;
            exit = false;
            switch (option) {
                case 1: //Create a new game for 2 players
                    responses = createGameFor2();
                    msg = (String) responses.get(0);
                    if (msg.equals(Config.CREATE_GAME_FOR_2_SUC))
                        exit = true;
                    else
                        System.out.println(msg);
                    break;
                case 2: //Create a new game for 3 players

                    break;
                case 3: //Create a new game for 4 players

                    break;
                case 4: //Resume an old game

                    break;
                case 5: //show existing games in the lobby
                    responses = getExistingGames();
                    msg = (String) responses.get(0);
                    if (msg.equals(Config.SHOW_EXISTING_GAMES_SUC))
                        exit = true;
                    else
                        System.out.println(msg);
                    break;
                case 6: //Show resumable games

                    break;
                case 7: //Join in a resumable game

                    break;
                case 8: //logout user
                    String res = logOut();
                    if (res.equals(Config.LOG_OUT_SUC))
                    {
                        System.out.println("Logout successfully");
                        sleep(1000);
                        exit = true;
                    }
                    else
                        System.out.println(res);
                    break;
                default:
                    System.out.println("Option not valid, please select a valid option.\n");
            }
        }
        while (!exit);

        switch (option)
        {
            case 1: //after creation enter to game room
                showCreatorGameRoom((Game) responses.get(1));
                break;
            case 5: //display all join-able game
                showExistingGames((ArrayList<Game>) responses.get(1));
                break;
            case 8: //after logout return to logging menu
                loggingMenu();
                break;
        }
    }
    public void run() {
        // TODO Auto-generated method stub
        try
        {
            //connectTOServer();
            loggingMenu();
            //lobbyMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private ArrayList<Object> createGameFor2()
    {

        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.CREATE_GAME_FOR_2);
        messages.add(userName);
        messages.add(true); //this is CLI
        ArrayList<Object> responses = responseFromServer(messages);

        return responses;
    }

    private ArrayList<Object> getExistingGames()
    {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.SHOW_EXISTING_GAMES);
        ArrayList<Object> responses = responseFromServer(messages);
        return responses;
    }

    private void showCreatorGameRoom(Game game)
    {
        clearScreen();
        System.out.println("This is creator's game room, only he can start the game.");
        System.out.println(String.format("Game mode: %s\n" +
                "needs %d participant\n" +
                        "waiting for %d",
                (game.isExpertMode()? "expert" : "normal"),
                game.getN_Player(),
                game.getN_Player()-game.getPlayers().size()
        ));
        // new UpdateListener(this,serverAddress,userName).start();

    }

    private void showOtherPlayerGameRoom(Game game)
    {
        clearScreen();
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
      //  new UpdateListener(this,serverAddress,userName).start();
    }

    /**
     * TODO
     * */
    private void showExistingGames(ArrayList<Game> games) throws InterruptedException, UnknownHostException {
        clearScreen();
        int i = 0;
        ArrayList<String> roomName = new ArrayList<>();
        for(Game g : games)
        {
            roomName.add(g.getPlayers().get(0).getName());
            System.out.println(String.format("%d. %s's game for %d, waiting for other %d.",
                    i+1,
                    g.getPlayers().get(0).getName(),
                    g.getN_Player(),
                    g.getN_Player()-g.getPlayers().size()
            ));
            i++;
        }
        System.out.println("Please select one that you want join, invalid enter will bring you previous page.");
        try
        {
            int choice = new Scanner(System.in).nextInt();
            ArrayList<Object> responses;
            if(choice >= 1 && choice <= i)
            {
                responses = joinOneGame(roomName.get(choice - 1),userName);
                String msg = (String) responses.get(0);
                if(msg.equals(Config.JOIN_ONE_GAME_SUC))
                {
                    showOtherPlayerGameRoom((Game) responses.get(1));
                }
                else
                {
                    System.out.println(msg);
                    sleep(1000);
                    lobbyMenu();
                }
            }
            else
                lobbyMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            lobbyMenu();
        }



    }
    private ArrayList<Object> joinOneGame(String creator, String player)
    {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.JOIN_ONE_GAME);
        messages.add(creator);
        messages.add(player);
        messages.add(true);// this is CLI
        ArrayList<Object> responses = responseFromServer(messages);
        return responses;
    }


    private String logOut()
    {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.LOG_OUT);
        messages.add(userName);
        ArrayList<Object> responses = responseFromServer(messages);
        return (String) responses.get(0);
    }


    private String loggingWithUserName() throws UnknownHostException {
        System.out.println("What's your username?");
        userName = new Scanner(System.in).nextLine();
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.USER_LOGGING);
        messages.add(userName);
        InetAddress iAddress = InetAddress.getLocalHost();
        String IP = iAddress.getHostAddress();
        messages.add(IP);
        messages.add(listeningPortNumber);
        ArrayList<Object> responses = responseFromServer(messages);
        return (String) responses.get(0);
    }

    private void connectTOServer()
    {
        System.out.println("Please insert ip address for connection to server:");
        serverAddress = new Scanner(System.in).nextLine();

        Socket client;

        String response = "";
        try {
            // client = new Socket("192.168.8.196", 12345);
            // client = new Socket(serverAddress, 12345);
            client = new Socket("localhost", 12345);
            System.out.println("Client ready.\n");
            // Input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // Output stream
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

            //sending a message to server
            out.println("try to connect");

            response = in.readLine().substring(4);

            System.out.println("message received is: "+response);
            //close client
            client.close();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void messagePrinter(String fileName)
    {
        try
        {
            String absolutePathToProject = new File("").getAbsolutePath();
            String pathFromContentRoot = "\\src\\main\\java\\it\\polimi\\ingsw\\storage\\";
            File file=new File(absolutePathToProject+pathFromContentRoot+fileName);    //creates a new file instance
            FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while((line=br.readLine())!=null)
            {
                sb.append(line);      //appends line to string buffer
                sb.append("\n");     //end of line
            }
            fr.close();    //closes the stream and release the resources
            System.out.println(sb.toString()+"\n");   //returns a string that textually represents the object
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private ArrayList<Object> responseFromServer(ArrayList<Object> messages)
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

    private void clearScreen()
    {
        for(int clear = 0; clear < 50; clear++)
        {
            System.out.println("\b") ;
        }
    }

    public String getUserName()
    {
        return userName;
    }

    /**
     * TODO
     * */
    public void update(ArrayList<Object> messages) throws EriantysExceptions {
        clearScreen();
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

    private void updateCreatorGameRoom(Game game) {
        clearScreen();
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
    private void updateOtherPlayerGameRoom(Game game)
    {
        clearScreen();
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

}
