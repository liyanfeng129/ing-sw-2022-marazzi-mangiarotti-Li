package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class Character6 extends CharacterCard implements Serializable {
    public Character6() {
        super();
        setCoin(3);
        String msg = "When resolving a Conquering on an Island,\n" +
                "Towers do not count towards influence.";
        setMsg(msg);
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if(!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character6 card = (Character6) game.getTable().findCharacterCardByName(this.name());
        game.getTable().setCard6(true);
        player.getWallet().removeCoin(card.getCoin());
        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: tower influence is not applied",
                player.getName(),this.name(), this.getCoin()));
        if(card.isFirstUse())
            card.setFirstUse(false);
        return true;
    }

    @Override
    public boolean undoEffect(Game game, Player player) throws EriantysExceptions {
        game.getTable().setCard6(false);
        return true;
    }

    @Override
    public boolean getData(Game game, boolean isCliClient) throws EriantysExceptions {
        if(isCliClient)
        {
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

    @Override
    public String toString() {
        return "Character6{" +
                "dataGathered=" + isDataGathered() +
                '}';
    }

    public String name()
    {
        return "Character6";
    }

}
