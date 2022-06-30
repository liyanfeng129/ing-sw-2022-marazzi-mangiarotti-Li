package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Character12 extends CharacterCard implements Serializable {
    int student_color = -1;
    public Character12() {
        super();
        setCoin(3);
        setN_card(12);
        String msg = "Choose a type of Student: every player\n" +
                "(including yourself) must return 3 Students of that type\n" +
                "from their Dining Room to the bag. If any player has\n" +
                "fewer than 3 Students of that type, return as many\n" +
                "Students as they have.";
        setMsg(msg);
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if (!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character12 card = (Character12) game.getTable().findCharacterCardByName(this.name());
        Bag bag = game.getTable().getBag();

        for (int i = 0; i < game.getN_Player(); i++) {
            int j=0;
            while (game.getPlayers().get(i).getPb().getDiningRoom()[student_color]>0){
                if(j==3)
                    break;
                else
                    game.getPlayers().get(i).getPb().takeStudentFromDiningRoom(student_color);
                    bag.addStudentToBag(student_color);
                    j++;
            }
        }
            game.getProfessors().assignProfessor(game.getPlayers());
            player.getWallet().removeCoin(card.getCoin());
            game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: student removed from dining room",
                    player.getName(), this.name(), this.getCoin(), SType.values()[student_color].name()));
            if (card.isFirstUse())
                card.setFirstUse(false);
            return true;
        }

    @Override
    public boolean undoEffect(Game game, Player player) throws EriantysExceptions {
        // this character has permanent effect on game, nothing has to be undone.
        return true;
    }

    @Override
    public boolean getData(Game game, boolean isCliClient) throws EriantysExceptions {
        if(isCliClient)
        {
            int choice;
                System.out.println("Which students color do you want to remove from dining room.");
                System.out.println("1: Red");
                System.out.println("2: Yellow");
                System.out.println("3: Pink");
                System.out.println("4: Blue");
                System.out.println("5: Green");
                choice = getInput() - 1;
            student_color = choice;
            setDataGathered(true);
            game.getLastCommand().setDataGathered(true);
        }

        return true;
    }

    /**
     * @param inputs
     * inputs.get(0) student_color : int
     *
     * inputs.get(1) game : Game
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        Game game = (Game) inputs.get(0);
        student_color = (int) inputs.get(0);
        setDataGathered(true);
        game.getLastCommand().setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }

    @Override
    public String toString() {
        return "Character12{" +
                "dataGathered=" + isDataGathered() +
                ", student_color=" + student_color +
                '}';
    }

    public String name()
    {
        return "Character12";
    }
    public int getStudent_color() {
        return student_color;
    }

    public void setStudent_color(int student_color) {
        this.student_color = student_color;
    }

}

