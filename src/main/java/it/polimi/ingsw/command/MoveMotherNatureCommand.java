package it.polimi.ingsw.command;

import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.model.Config;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.state.ActionState;
import it.polimi.ingsw.state.MoveMotherNatureState;
import it.polimi.ingsw.view.Cli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveMotherNatureCommand extends Command implements Serializable {
    private int steps;
    private int maxSteps;
    private int characterIndex = -1;
    private boolean characterCardUsed;
    private boolean characterCardExecuted;


    public MoveMotherNatureCommand(boolean isCliClient, Game game, String username,int maxSteps,boolean characterCardUsed,boolean characterCardExecuted) {
        super(isCliClient, game, username);
        this.maxSteps = maxSteps;
        this.characterCardUsed = characterCardUsed;
        this.characterCardExecuted = characterCardExecuted;
    }


    @Override
    public void undo(Game game) {

    }
    public void getData() {
        if (!isDataGathered()) {
            if (isCliClient()) {
                if(getGame().isExpertMode() && !characterCardUsed) {
                    int coin = getGame().getTurnList().get(getGame().getGameState().getPhase()).getWallet().getSaving();
                    int choice;
                    int quit=0;
                    System.out.println("Digit 10 if you want to use a character");
                    int input = getInput();
                    if (input == 10) {
                        /*
                        which character do you want to use
                        get this character
                        * */
                        Cli c = new Cli();
                        for (CharacterCard card : getGame().getTable().getCharacters())
                            c.show_character(card);
                        do {
                            System.out.println("Choose form 1 to 3, 10 to quit");
                            choice = getInput() - 1;
                            if(choice==9) {
                                quit=1;
                                break;
                            }
                            else {
                                if (!(choice < 0 || choice > 2)) {
                                    characterIndex = choice;
                                    if (getGame().getTable().getCharacters().get(characterIndex).getCoin() > coin) {
                                        System.out.println("not enough money");
                                        choice = -1;
                                    }
                                }
                            }
                        } while (choice<0 || choice>2);
                        if(quit!=1)
                            characterCardUsed = true;
                        else
                            getDataForMoveMotherNature();
                    } else
                        getDataForMoveMotherNature();
                }
                else
                {
                    getDataForMoveMotherNature();
                }
            }
        }
        setDataGathered(true);
    }

    public void  getDataForMoveMotherNature()
    {
        if(!isDataGathered())
        {
            if(isCliClient())
            {
                int choice;
                do
                {
                    System.out.println("How many steps do you want to take?.\n"+
                            "You have "+maxSteps+" steps");
                    choice = getInput();
                }
                while(choice < 1 || choice >   maxSteps);
                steps = choice;
                setDataGathered(true);
            }
        }
    }
    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        if(!characterCardUsed || characterCardExecuted)
            return normalExecute(game);
        else
        if(game.getGameState() instanceof MoveMotherNatureState)
        {
            ((MoveMotherNatureState)game.getGameState()).setCharacterCardUsed(characterCardUsed);
            ((MoveMotherNatureState)game.getGameState()).setCharacterIndex(characterIndex);
        }
        return true;
    }

    public boolean normalExecute(Game game) throws EriantysExceptions {
        if(isDataGathered())
        {
            game.getTable().moveMotherNature(steps);
            setMsg(String.format("Player %s moved the mother nature %d steps in forward.", getUsername(), steps));
            return true;
        }
        return false;
    }


    /**
     * @param inputs
     * inputs.get(1) steps: int (selected island position - island position where lands mother nature)
     * @return
     * Config.GUI_COMMAND_GETDATA_SUC  if ok
     * Config.GUI_WRONG_STEPS if ko
     * */

    /**
     * @param inputs
     * inputs.get(0) : useCharacter boolean
     *      true -> inputs.get(1): characterIndex int
     *
     *      false -> inputs.get(1) steps: int (selected island position - island position where lands mother nature)
     *
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) {
        boolean useCharacter = (boolean) inputs.get(0);
        if(!useCharacter)
        {
            int steps = (int) inputs.get(1);
            if(steps > maxSteps || steps < 1)
                return Config.GUI_WRONG_STEPS;
            this.steps = steps;
            setDataGathered(true);
            return Config.GUI_COMMAND_GETDATA_SUC;
        }
        else {
            if(!characterCardUsed)
            {
                int characterIndex = (int) inputs.get(1);
                int coin = getGame().getTurnList().get(getGame().getGameState().getPhase()).getWallet().getSaving();
                if (getGame().getTable().getCharacters().get(characterIndex).getCoin() > coin) // cost higher than what you have
                    return Config.GUI_NOT_ENOUGH_COIN;
                else
                {
                    this.characterIndex = characterIndex;
                    characterCardUsed = true;
                    setDataGathered(true);
                    return Config.GUI_COMMAND_GETDATA_SUC;
                }
            }
            else
                return Config.GUI_CHARACTER_ALREADY_USED;

        }
    }
}
