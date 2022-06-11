package it.polimi.ingsw.client;


import it.polimi.ingsw.model.*;


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
    private  int listeningPortNumber;
    private UpdateReceiver ur = null;
    private  void loggingMenu() throws InterruptedException, UnknownHostException {
        clearScreen();
        String response;
        listeningPortNumber = new Random().nextInt(65353);
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
            ur = new UpdateReceiver(listeningPortNumber,userName,serverAddress);
            ur.start();
            lobbyMenu();
        }
    }
    private void lobbyMenu() throws InterruptedException, UnknownHostException {
        /**
         * 1. Create a new game for 2 players
         * 2. Create a new game for 3 players
         * 3. Create a new expert game for 2 players
         * 4. Create a new expert game for 3 players
         * 5. Resume an old game
         * 6. Show existing games in the lobby
         * 7. Show resumable games
         * 8. Join in a resumable game
         * 9. logout and exit
         * */
        clearScreen();
        ArrayList<Object> responses;
        ArrayList<Object> messages;
        int option = -1;
        boolean exit;
        do {
            responses = new ArrayList<>();
            messages = new ArrayList<>();
            messagePrinter("welcome.txt");
            option = new Scanner(System.in).nextInt();
            String msg;
            exit = false;
            switch (option) {
                case 1: //Create a new game for 2 players
                    responses = createNormalGameFor2();
                    msg = (String) responses.get(0);
                    if (msg.equals(Config.CREATE_NORMAL_GAME_FOR_2_SUC))
                        exit = true;
                    else
                        System.out.println(msg);
                    break;
                case 2: //Create a new game for 3 players
                    responses = createNormalGameFor3();
                    msg = (String) responses.get(0);
                    if (msg.equals(Config.CREATE_NORMAL_GAME_FOR_3_SUC))
                        exit = true;
                    else
                        System.out.println(msg);
                    break;
                case 3: //Create a new expert game for 2 players
                    responses = createExpertGameFor2();
                    msg = (String) responses.get(0);
                    if (msg.equals(Config.CREATE_EXPERT_GAME_FOR_2_SUC))
                        exit = true;
                    else
                        System.out.println(msg);
                    break;
                case 4: //Create a new expert game for 3 players

                    break;
                case 5: //Resume an old game
                    messages.add(Config.RESUME_OLD_GAMES);
                    messages.add(userName);
                    responses = responseFromServer(messages);
                    msg = (String) responses.get(0);
                    if(msg.equals(Config.RESUME_OLD_GAMES_SUC))
                        exit = true;
                    else
                        System.out.println(msg);
                    break;
                case 6: //show existing games in the lobby
                    responses = getExistingGames();
                    msg = (String) responses.get(0);
                    if (msg.equals(Config.SHOW_EXISTING_GAMES_SUC))
                        exit = true;
                    else
                        System.out.println(msg);
                    break;
                case 7: //Show resumable games
                    responses = getJoinAbleResuableGames();
                    msg = (String) responses.get(0);
                    if (msg.equals(Config.SHOW_RESUMABLE_GAMES_SUC))
                        exit = true;
                    else
                        System.out.println(msg);
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
            case 2:
            case 3:
            case 4:
                showCreatorGameRoom((Game) responses.get(1));
                break;
            case 5: // resume old game created by user
                getResumeableGames((ArrayList<Game>) responses.get(1));
                break;
            case 6: //display all join-able game
                showExistingGames((ArrayList<Game>) responses.get(1));
                break;
            case 7: //display all join_able old game
                //showExistingGames((ArrayList<Game>) responses.get(1));
                showResuableGames((ArrayList<Game>) responses.get(1));
                break;
            case 8: //after logout return to logging menu
                loggingMenu();
                break;
        }
    }

    private void showResuableGames(ArrayList<Game> games) throws UnknownHostException, InterruptedException {
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
        if(roomName.size() > 0)
        {
            System.out.println("Please select one that you want join, invalid enter will bring you previous page.");
            try
            {
                int choice = new Scanner(System.in).nextInt();
                ArrayList<Object> responses;
                if(choice >= 1 && choice <= i)
                {
                    responses = joinOneResumaleGame(roomName.get(choice - 1),userName);
                    String msg = (String) responses.get(0);
                    if(msg.equals(Config.JOIN_RESUMABLE_GAME_SUC))
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
        else
        {
            System.out.println("There's no old join-able game for you, maybe start a new one.");
            lobbyMenu();
        }
    }

    private ArrayList<Object> joinOneResumaleGame(String roomName, String userName) {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.JOIN_RESUMABLE_GAME);
        messages.add(roomName);
        messages.add(userName);
        messages.add(true);// this is CLI
        ArrayList<Object> responses = responseFromServer(messages);
        return responses;
    }

    private ArrayList<Object> getJoinAbleResuableGames() {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.SHOW_RESUMABLE_GAMES);
        messages.add(userName);
        return responseFromServer(messages);
    }


    public void run() {
        // TODO Auto-generated method stub
        try
        {
            onClientClose();
            //connectTOServer();
            loggingMenu();
            //lobbyMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private ArrayList<Object> createNormalGameFor2()
    {

        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.CREATE_NORMAL_GAME_FOR_2);
        messages.add(userName);
        messages.add(true); //this is CLI
        ArrayList<Object> responses = responseFromServer(messages);

        return responses;
    }

    private ArrayList<Object> createNormalGameFor3()
    {

        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.CREATE_NORMAL_GAME_FOR_3);
        messages.add(userName);
        messages.add(true); //this is CLI
        ArrayList<Object> responses = responseFromServer(messages);

        return responses;
    }

    private ArrayList<Object> createExpertGameFor2() {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.CREATE_EXPERT_GAME_FOR_2);
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
        System.out.println("This is creator's game room, only he can start the game.\n");
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

    private void getResumeableGames(ArrayList<Game> games) throws UnknownHostException, InterruptedException {
        clearScreen();
        int i = 0;
        ArrayList<String> gameDates = new ArrayList<>();
        for(Game g : games)
        {
            gameDates.add(g.getGameStartingTime());
            System.out.println(String.format("%d. %s's %s game for %d, started in %s with player: %s, %s .",
                    i+1,
                    g.getTurnList().get(0).getName(),
                    (g.isExpertMode()? "expert" : "normal"),
                    g.getN_Player(),
                    g.getGameStartingTime(),
                    (g.getN_Player() > 1 ? g.getTurnList().get(1).getName() : " " ),
                    (g.getN_Player() > 2 ? g.getTurnList().get(2).getName() : " " )
            ));
            i++;
        }
        System.out.println("Please select one that you want restart, invalid enter will bring you previous page.");
        try
        {
            int choice = new Scanner(System.in).nextInt();
            ArrayList<Object> responses;
            if(choice >= 1 && choice <= i)
            {
                responses = resumeAnOldGame(gameDates.get(choice -1), userName);
                String msg = (String) responses.get(0);
                if(msg.equals(Config.RELOAD_AN_OLD_GAME_SUC))
                {
                    showCreatorGameRoom((Game) responses.get(1));
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

    private ArrayList<Object> resumeAnOldGame(String gameStartedDate, String name)
    {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.RELOAD_AN_OLD_GAME);
        messages.add(name);
        messages.add(gameStartedDate);
        return responseFromServer(messages);
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
        ur.setReceiverOn(false);
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

    private void onClientClose() throws Exception{
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {
                    System.out.println("Closing connection");
                    if(ur!=null && ur.isReceiverOn())
                        logOut();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
