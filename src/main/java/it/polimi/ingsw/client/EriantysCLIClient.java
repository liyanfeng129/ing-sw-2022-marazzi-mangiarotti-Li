package it.polimi.ingsw.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EriantysCLIClient {
    public static void main(String[] args)  {
        // TODO Auto-generated method stub

        welcomeMessage();
        //String filePath = new File("").getAbsolutePath();
        //System.out.println("path is:"+filePath);


    }
    private static void welcomeMessage()
    {
        try
        {
            String absolutePathToProject = new File("").getAbsolutePath();
            String pathFromContentRoot = "\\src\\main\\java\\it\\polimi\\ingsw\\storage\\welcome.txt";
            File file=new File(absolutePathToProject+pathFromContentRoot);    //creates a new file instance
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


    private static void socketClient()
    {
        Socket client;
        String message="";

        Scanner receiveFromKeyBoard =new Scanner(System.in);
        try {
            // client = new Socket("192.168.8.196", 12345);
            client = new Socket("localhost", 12345);
            System.out.println("Client ready.\n");
            // Input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // Output stream
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

            //sending a message to server
            out.println("connected");
            message = receiveFromKeyBoard.nextLine();
            out.println(message);

            //close client
            client.close();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
