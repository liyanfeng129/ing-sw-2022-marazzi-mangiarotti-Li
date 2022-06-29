package it.polimi.ingsw.GUI;

import it.polimi.ingsw.command.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import it.polimi.ingsw.model.*;


public class GuiMessageSender {
    private AASceneParent caller;
    private String userName;
    private String serverAddress;
    private String option;
    private ArrayList<Object> responses;

    public GuiMessageSender(AASceneParent caller, String option) {
        this.caller = caller;
        userName = caller.getInfo().getUserName();
        serverAddress = caller.getInfo().getServerAddress();
        this.option = option;
    }

    public void run()
    {
        try
        {
            ArrayList<Object> messages = new ArrayList<>();
            responses = new ArrayList<>();
            switch (option)
            {
                case Config.LOG_OUT:
                    responses = logOut();
                    caller.responsesFromSender(responses);
                    break;
                case Config.TRY_TO_CONNECT:
                    responses = connectToServer();
                    caller.responsesFromSender(responses);
                    break;
                case Config.USER_LOGGING:
                    responses = loggingWithUserName();
                    caller.responsesFromSender(responses);
                    break;
                case Config.CREATE_NORMAL_GAME_FOR_2:
                    responses = createNormalGameFor2();
                    caller.responsesFromSender(responses);
                    break;
                case Config.CREATE_NORMAL_GAME_FOR_3:
                    responses = createNormalGameFor3();
                    caller.responsesFromSender(responses);
                    break;
                case Config.CREATE_EXPERT_GAME_FOR_2:
                    responses = createExpertGameFor2();
                    caller.responsesFromSender(responses);
                    break;
                case Config.SHOW_EXISTING_GAMES:
                    responses = getExistingGames();
                    caller.responsesFromSender(responses);
                    break;
                case Config.JOIN_ONE_GAME:
                    responses = joinOneGame(caller.getInfo().getGameCreatorName(),caller.getInfo().getUserName());
                    caller.responsesFromSender(responses);
                    break;
                case Config.GAME_START:
                    responses = startGame(caller.getInfo().getUserName());
                    caller.responsesFromSender(responses);
                    break;
                case Config.COMMAND_EXECUTE:
                    responses = commandExecute(caller.getInfo().getUserName(), caller.getInfo().getCommand());
                    caller.responsesFromSender(responses);
                    break;
                default:
            }
        }
        catch (Exception e)
        {
            caller.errorCommunicate(e);
        }
    }

    private ArrayList<Object> connectToServer() throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.TRY_TO_CONNECT);
        ArrayList<Object> responses = responseFromServer(messages);
        return responses;
    }

    private ArrayList<Object> commandExecute(String userName, Command command) throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        ArrayList<Object> responses = new ArrayList<>();
        messages.add(Config.COMMAND_EXECUTE);
        messages.add(userName);
        messages.add(command);
        responses = responseFromServer(messages);
        return responses;
    }

    private ArrayList<Object> startGame(String userName) throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.GAME_START);
        messages.add(userName);
        ArrayList<Object> responses = responseFromServer(messages);
        return responses;
    }


    private ArrayList<Object> createNormalGameFor2() throws IOException, ClassNotFoundException {

        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.CREATE_NORMAL_GAME_FOR_2);
        messages.add(userName);
        messages.add(true); //this is CLi
        ArrayList<Object> responses = responseFromServer(messages);

        return responses;
    }

    private ArrayList<Object> createNormalGameFor3() throws IOException, ClassNotFoundException {

        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.CREATE_NORMAL_GAME_FOR_3);
        messages.add(userName);
        messages.add(false); //this is not CLi
        ArrayList<Object> responses = responseFromServer(messages);

        return responses;
    }

    private ArrayList<Object> createExpertGameFor2() throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.CREATE_EXPERT_GAME_FOR_2);
        messages.add(userName);
        messages.add(false); //this is not CLi
        ArrayList<Object> responses = responseFromServer(messages);

        return responses;
    }


    private ArrayList<Object> getExistingGames() throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.SHOW_EXISTING_GAMES);
        ArrayList<Object> responses = responseFromServer(messages);
        return responses;
    }

    private ArrayList<Object> resumeAnOldGame(String gameStartedDate, String name) throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.RELOAD_AN_OLD_GAME);
        messages.add(name);
        messages.add(gameStartedDate);
        return responseFromServer(messages);
    }

    private ArrayList<Object> joinOneGame(String creator, String player) throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.JOIN_ONE_GAME);
        messages.add(creator);
        messages.add(player);
        messages.add(false);// this is not CLi
        ArrayList<Object> responses = responseFromServer(messages);
        return responses;
    }


    private ArrayList<Object> logOut() throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.LOG_OUT);
        messages.add(userName);
        ArrayList<Object> responses = responseFromServer(messages);
        //ur.setReceiverOn(false);
        return responses;
    }

    private ArrayList<Object> responseFromServer(ArrayList<Object> messages) throws IOException, ClassNotFoundException {
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

    private ArrayList<Object> loggingWithUserName() throws IOException, ClassNotFoundException {
        ArrayList<Object> messages = new ArrayList<>();
        messages.add(Config.USER_LOGGING);
        messages.add(userName);
        InetAddress iAddress = InetAddress.getLocalHost();
        String IP = iAddress.getHostAddress();
        messages.add(IP);
        messages.add(caller.getInfo().getListeningPortNumber());
        ArrayList<Object> responses = responseFromServer(messages);
        return responses;
    }


}
