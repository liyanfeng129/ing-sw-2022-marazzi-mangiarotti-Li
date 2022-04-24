package it.polimi.ingsw.server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EriantysServer {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try
        {
            //Create socket server
            ServerSocket server = new ServerSocket(12345);

            InetAddress iAddress = InetAddress.getLocalHost();
            String server_IP = iAddress.getHostAddress();
            System.out.println("Server IP address : " +server_IP);
            

            // declare socket client
            Socket client;

            String message;

            System.out.println("Server ready");


            while(true)
            {
                // waiting for a client to connect
                client = server.accept();
                System.out.println("Client connected: "+client);

                // Input stream
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                // Output stream
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

                message = in.readLine();
                switch (message)
                {
                    case "1":
                        System.out.println(client+"asking for option 1");
                        out.println("ok, option 1");
                        break;
                    case "2":
                        System.out.println(client+"asking for option 2");
                        out.println("ok, option 2");
                        break;
                    case "try to connect":
                        System.out.println(client+"trying to connect");
                        out.println("ok, you are connected");
                        break;

                    default:
                        out.println("not ok, no option valid for this");
                }
                // close client
                client.close();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
