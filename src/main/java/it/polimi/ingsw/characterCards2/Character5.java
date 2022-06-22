package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Character5 extends CharacterCard implements Serializable {
    int island_pos = -1;
    int no_entry_tile;
    public Character5() {
        super();
        setCoin(2);
        setN_card(5);
        String msg = "Place a No Entry tile on an Island of your choice.\n" +
                "The first time Mother Nature ends her movement\n" +
                "there, put the No Entry tile back onto this card DO NOT\n" +
                "calculate influence on that Island, or place any Towers.";
        setMsg(msg);
        this.no_entry_tile=4;
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character5 card = (Character5) game.getTable().findCharacterCardByName(this.name());
        player.getWallet().removeCoin(card.getCoin());

        if (getNo_entry_tile()<1)
            System.out.println("there aren't no_entry_tile left");
        else {
            game.getTable().getIsland(island_pos).setNoEntryTiles(true);
            card.useEntyTile();
        }

        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: one no entry tile has been placed to island %d",
                player.getName(),this.name(), this.getCoin(), island_pos));
        if(card.isFirstUse())
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
            int islands_size = game.getTable().getIslands().size();
            do {
                System.out.println(String.format("Select one island from 1 to %d ", islands_size));
                choice = new Scanner(System.in).nextInt();
            }
            while (choice < 1 || choice > islands_size);
            island_pos = choice - 1;
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
     * inputs.get(0) island_pos : int
     *
     * inputs.get(1) game : Game
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        island_pos = (int) inputs.get(0);
        Game game = (Game) inputs.get(1);
        setDataGathered(true);
        game.getLastCommand().setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }

    @Override
    public String toString() {
        return "Character5{" +
                "dataGathered=" + isDataGathered() +
                ", no entry tiles =" + no_entry_tile +
                '}';
    }

    public String name()
    {
        return "Character5";
    }

    public void useEntyTile()
    {
        this.no_entry_tile--;
    }
    public void takeEntryTile(){
        this.no_entry_tile++;
    }

    public int getNo_entry_tile() {
        return no_entry_tile;
    }

    public void setNo_entry_tile(int no_entry_tile) {
        this.no_entry_tile = no_entry_tile;
    }

    public int getIsland_pos() {
        return island_pos;
    }

    public void setIsland_pos(int island_pos) {
        this.island_pos = island_pos;
    }
}

