package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Character4 extends CharacterCard implements Serializable {
    public Character4() {
        super();
        setCoin(1);
        setN_card(4);
        String msg = "You may move Mother Nature up to 2\n" +
                "additional Islands than is indicated by the Assistant\n" +
                "card you've played.";
        setMsg(msg);
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if (!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character4 card = (Character4) game.getTable().findCharacterCardByName(this.name());
        player.getWallet().removeCoin(card.getCoin());
        int max_steps = player.getHand().getLastPlayedCard();
        player.getHand().setLastPlayedCard(max_steps + 2);
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: you have 2 extra steps",
                player.getName(), this.name(), this.getCoin()));
        if (card.isFirstUse())
            card.setFirstUse(false);
        return true;
    }

    @Override
    public boolean undoEffect(Game game, Player player) throws EriantysExceptions {
        //penso serva solo per la grafica,in teorian e inutile
        int max_steps = player.getHand().getLastPlayedCard();
        player.getHand().setLastPlayedCard(max_steps - 2);
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

    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        return null;
    }

    @Override
    public String toString() {
        return "Character4{" +
                "dataGathered=" + isDataGathered() +
                '}';
    }

    public String name() {
        return "Character4";
    }
}