package it.polimi.ingsw.client;



import it.polimi.ingsw.GUI.GameStarted;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
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

        System.out.println(test2.class.getClass().getResource("game_started.fxml").getPath());

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
