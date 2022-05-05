package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Config;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EriantysCLIClient {
     static String serverAddress = "localhost";
     static String userName = "";
    public static void main(String[] args)  {
        // TODO Auto-generated method stub


        //connectTOServer();
        String response;
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

        int option = -1;
        do {
            messagePrinter("welcome.txt");
            option = new Scanner(System.in).nextInt();
            switch (option)
            {
                case 1: //Create a new game for 2 players

                    break;
                case 5: //show existing games in the lobby

                    break;
                case 8: //logout user
                    String res = logOut();
                    if(res.equals(Config.LOG_OUT_SUC))
                        System.out.println("Logout successfully");
                    else
                        System.out.println(res);
                    break;
            }
        }
        while(option != 8);


    /*
            for(int i =0; i<10; i++)
        {
            String message = new Scanner(System.in).nextLine();
            System.out.println(sendReceiveMessageFromServer(message));
        }


     */

            //socketClient();
        //String filePath = new File("").getAbsolutePath();
        //System.out.println("path is:"+filePath);


    }

    private static String createGameFor2()
    {

        try
        {
            Socket client = new Socket(serverAddress, 12345);
            // Input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // Output stream
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            out.println(Config.CREATE_GAME_FOR_2);
            out.println(userName);
            String res = in.readLine().substring(4);
            client.close();
            return res;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return "unknown error occurred";
    }


    private static String logOut()
    {
        try
        {
            Socket client = new Socket(serverAddress, 12345);
            // Input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // Output stream
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            out.println(Config.LOG_OUT);
            out.println(userName);
            String res = in.readLine().substring(4);
            client.close();
            return res;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return "unknown error occurred";
    }


    private static String loggingWithUserName()
    {
        System.out.println("What's your username?");
        userName = new Scanner(System.in).nextLine();
        try
        {
            Socket client = new Socket(serverAddress, 12345);
            // Input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // Output stream
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            out.println("logging");
            out.println(userName);
            String res = in.readLine().substring(4);
            client.close();
            return res;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "Unknown error occurred ";
    }

    private static String sendReceiveMessageFromServer(String message)
    {
        try
        {
            Socket client = new Socket(serverAddress, 12345);
            // Input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // Output stream
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            out.println(message);
            String response = in.readLine().substring(4);
            client.close();
            return response;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    private static void connectTOServer()
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




    private static void messagePrinter(String fileName)
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
            System.out.println(sb.toString());   //returns a string that textually represents the object
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
