package it.polimi.ingsw.server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EriantysServer {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try
        {
            //Create socket server
            ServerSocket server = new ServerSocket(12345);

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
                    case "":

                        break;
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
