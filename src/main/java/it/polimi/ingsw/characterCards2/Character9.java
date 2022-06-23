package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Character9 extends CharacterCard implements Serializable {
    int student_color=-1;
    public Character9() {
        super();
        setCoin(3);
        setN_card(9);
        String msg = "Choose a color of Student: during the influence\n" +
                "calculation this turn, that color adds no influence.";
        setMsg(msg);
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character9 card = (Character9) game.getTable().findCharacterCardByName(this.name());
        game.getTable().setCard9(student_color);
        player.getWallet().removeCoin(card.getCoin());
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: this color will not add influence",
                player.getName(),this.name(), this.getCoin()));
        if(card.isFirstUse())
            card.setFirstUse(false);
        return true;
    }

    @Override
    public boolean undoEffect(Game game, Player player) throws EriantysExceptions {
        this.student_color=-1;
        return true;
    }

    @Override
    public boolean getData(Game game, boolean isCliClient) throws EriantysExceptions {
        if(isCliClient)
        {
            int choice;

                System.out.println("Which students color do you want to ignore .");
                System.out.println("1: Red");
                System.out.println("2: Yellow");
                System.out.println("3: Pink");
                System.out.println("4: Blue");
                System.out.println("5: Green");
                choice = new Scanner(System.in).nextInt() - 1;
            student_color = choice;
            setDataGathered(true);
            game.getLastCommand().setDataGathered(true);
        }
        else
        {
            /**TODO
             * GUI get data
             * */
        }
        return true;
    }
    /**
     * @param inputs
     * inputs.get(0) student_color : int
     *
     * input.get(1) game Game
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        Game game = (Game) inputs.get(1);
        student_color = (int) inputs.get(0);
        setDataGathered(true);
        game.getLastCommand().setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }

    @Override
    public String toString() {
        return "Character9{" +
                "dataGathered=" + isDataGathered() +
                ", student_color=" + student_color +
                '}';
    }

    public String name()
    {
        return "Character9";
    }

}
