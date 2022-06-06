package it.polimi.ingsw.characterCards2;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Cli;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class Character3 extends CharacterCard implements Serializable {
    int island_pos = -1;

    public Character3() {
        super();
        setCoin(3);
        setN_card(3);
        String msg = "Choose an Island and resolve the Island as if\n" +
                "Mother Nature had ended her movement there. Mother\n" +
                "Nature will still move and the Island where she ends\n" +
                "her movement will also be resolved.";
        setMsg(msg);
    }

    @Override
    public boolean useCard(Game game, Player player) throws EriantysExceptions {
        if (!isDataGathered())
            throw new InnerExceptions.CharacterCardError("Cannot apply character effect because the lack of information.");
        Character3 card = (Character3) game.getTable().findCharacterCardByName(this.name());
        player.getWallet().removeCoin(card.getCoin());
        if(!game.getTable().getIsland(island_pos).isNoEntryTiles()) {
            int MN_prec = game.getTable().getMotherNatureIndex();
            game.getTable().getIsland(MN_prec).setMotherNature(false);
            Island island=game.getTable().getIsland(island_pos);
            int MN_temp = game.getTable().getIslands().indexOf(island);
            game.getTable().getIsland(MN_temp).setMotherNature(true);
            for (int i = 0; i < game.getN_Player(); i++) {
                if (game.getTable().getPlayerMaxInfluence(game) == game.getPlayers().get(i))
                    game.getTable().getIsland(game.getTable().getMotherNatureIndex()).setTower(game.getPlayers().get(i).getTowerColor());
            }
            game.getTable().mergeIsland();
            //dove si sposta madre natura in caso di merge?
            game.getTable().getIsland(MN_temp).setMotherNature(false);
            game.getTable().getIsland(MN_prec).setMotherNature(true);
        }
        else{
            game.getTable().getIsland(island_pos).setNoEntryTiles(false);
            //devo aggiungere la no entrytiles alla carta 5
            Character5 card5 = (Character5) game.getTable().findCharacterCardByName("Character5");
            card5.takeEntryTile();

        }


        game.getLastCommand().setMsg(String.format("Player %s used %s, spending %d coin: resolving influence on island %d",
                player.getName(), this.name(), this.getCoin(), island_pos));
        if (card.isFirstUse())
            card.setFirstUse(false);
        return true;
    }

    @Override
    public boolean undoEffect(Game game, Player player) throws EriantysExceptions {

        return true;
    }

    @Override
    public boolean getData(Game game, boolean isCliClient) throws EriantysExceptions {
        if (isCliClient) {
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
        } else {
            /**TODO
             * GUI get data
             * */
        }
        return true;
    }

    @Override
    public String toString() {
        return "Character3{" +
                "dataGathered=" + isDataGathered() +
                ", island_pos=" + island_pos +
                '}';
    }

    public String name() {
        return "Character3";
    }
}