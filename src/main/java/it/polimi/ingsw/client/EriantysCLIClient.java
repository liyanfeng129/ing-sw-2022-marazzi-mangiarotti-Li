package it.polimi.ingsw.client;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.Subscriber;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class EriantysCLIClient {

    public boolean needUpdate = false;
    private static String serverAddress = "localhost";
    private static String userName = "";
    public static void main(String[] args)  {
        // TODO Auto-generated method stub
        try
        {
            //connectTOServer();
            //loggingMenu();
            //lobbyMenu();
            new EriantysCLIClientThread().start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private static void onClientClose() throws Exception{
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
