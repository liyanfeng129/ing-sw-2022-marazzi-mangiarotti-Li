package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Character2 extends CharacterCard implements Serializable {
    public Character2() {
        super();
        setN_card(2);
        setCoin(2);
        String msg = "During this turn, you take control of any\n" +
                "number of Professors even if you have the same\n" +
                "number of Students as the player who currently\n" +
                "controls them.";
        setMsg(msg);
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if (!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character2 card = (Character2) game.getTable().findCharacterCardByName(this.name());
        for (int i = 0; i < 5; i++) {
            player.getPlayerBoard().addStudentToHolder(i);
        }
        //non so se ci voglia o no
        game.getProfessors().assignProfessor(game.getPlayers());
        player.getWallet().removeCoin(card.getCoin());
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: professor assignment is changed",
                player.getName(), this.name(), this.getCoin()));
        if (card.isFirstUse())
            card.setFirstUse(false);
        return true;
    }

    @Override
    public boolean undoEffect(Game game, Player player) throws EriantysExceptions {
        for (int i = 0; i < 5; i++) {
            player.getPlayerBoard().takeStudentFromHolder(i);
        }
        game.getProfessors().assignProfessor(game.getPlayers());
        return true;
    }

    @Override
    public boolean getData(Game game, boolean isCliClient) throws EriantysExceptions {
        if (isCliClient) {
            setDataGathered(true);
            game.getLastCommand().setDataGathered(true);
        } else {
            /**TODO
             * GUI get data
             * */
        }
        return true;
    }

    /**
     * @param inputs
     * inputs.get(0) game : Game
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        Game game = (Game) inputs.get(0);
        setDataGathered(true);
        game.getLastCommand().setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }

    @Override
    public String toString() {
        return "Character2{" +
                "dataGathered=" + isDataGathered() +
                '}';
    }

    public String name() {
        return "Character2";
    }
}