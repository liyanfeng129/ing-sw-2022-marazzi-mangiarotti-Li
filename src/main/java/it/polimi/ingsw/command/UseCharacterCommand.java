package it.polimi.ingsw.command;

import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.*;
import java.io.Serializable;
import java.util.ArrayList;

public class UseCharacterCommand extends Command implements Serializable {
    private CharacterCard card;
    public UseCharacterCommand(boolean isCliClient, Game game, String username, CharacterCard card) {
        super(isCliClient, game, username);
        this.card = card;
    }

    @Override
    public void undo(Game game) throws EriantysExceptions {
        Player p = game.findPlayerByName(getUsername());
        card.undoEffect(game, p);
    }

    @Override
    public void getData() throws EriantysExceptions {
        if(card.getData(getGame(),isCliClient()))
            System.out.println("All data for this character card gathered successfully");
        else
            System.out.println("Something went wrong with this character card");
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        Player p = game.findPlayerByName(getUsername());
        return  card.useCard(game,p);
    }

    @Override
    public String GUIGetData(ArrayList<Object> inputs) throws EriantysExceptions {
        inputs.add(getGame());
        return card.GUIGetData(inputs);
    }
}
