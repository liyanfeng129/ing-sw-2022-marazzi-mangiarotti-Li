package it.polimi.ingsw.client;



import it.polimi.ingsw.GUI.GameStarted;
import it.polimi.ingsw.GUI.HomeApplication;
import it.polimi.ingsw.GUI.LoadGame;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class test2 implements Serializable {
    //public static Player player = new Player("abc", Mage.MAGE1, TowerColor.BLACK,2,true, true);
    //public static Player player2 = new Player("def", Mage.MAGE2, TowerColor.GREY, 2, true, true);

    public static void main(String[] args) throws EriantysExceptions, CloneNotSupportedException, InterruptedException, ParseException {


        String absolutePathToProject = new File("").getAbsolutePath();

        System.out.println(absolutePathToProject);


        URL resource = LoadGame.class.getResource("game_set_up.fxml");
        URL resource1 = LoadGame.class.getResource("storage/welcome.txt");
        File file = new File(resource1.getPath());
        try
        {
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
            System.out.println(sb.toString()+"\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(resource1.getPath());
        //System.out.println(HomeApplication.class.getClass().getResource("game_started.fxml").getPath());

    }

    public static int TimeDiff(LocalDateTime t1, LocalDateTime t2) throws InterruptedException {

        return (int) ChronoUnit.SECONDS.between(t1, t2);

    }


    public static Object fileBin2Object(String fileName)
    {
        String absolutePathToProject = new File("").getAbsolutePath();
        String pathFromContentRoot = Config.PATH_FROM_CONTENT_ROOT;
        try
        {
            FileInputStream fi = new FileInputStream(new File(absolutePathToProject+pathFromContentRoot+fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);
            return oi.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
    public static void updateGame(Game game)
    {
        System.out.println(game);
    }


}
