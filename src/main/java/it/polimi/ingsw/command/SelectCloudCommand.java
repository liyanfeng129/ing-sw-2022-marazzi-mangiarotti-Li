package it.polimi.ingsw.command;

import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.state.MoveMotherNatureState;
import it.polimi.ingsw.state.TakeCloudState;
import it.polimi.ingsw.view.Cli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class SelectCloudCommand extends Command implements Serializable{
    private int cloud;
    private int characterIndex = -1;
    private boolean characterCardUsed;
    private boolean characterCardExecuted;


    public SelectCloudCommand(boolean isCliClient, Game game, String username,boolean characterCardUsed,boolean characterCardExecuted) {
        super(isCliClient, game, username);
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
                            getDataForSelectCloud();
                    } else
                        getDataForSelectCloud();
                }
                else
                {
                    getDataForSelectCloud();
                }
            }
        }
        setDataGathered(true);
    }
    public void  getDataForSelectCloud()
    {
        if(!isDataGathered())
        {
            if(isCliClient())
            {
                int choice;
                int n_clouds=getGame().getTable().getClouds().size();
                if (!getGame().isStudentFinished()) {
                    do {
                        System.out.println(String.format("Select one cloud from 1 to %d ", n_clouds));
                        choice = getInput() - 1;
                        if (Arrays.stream(getGame().getTable().getClouds().get(choice).getStudents()).sum() == 0) {
                            System.out.println("Cloud is empty, please try again.");
                            choice = -1;
                        }
                    }
                    while (choice < 0 || choice > n_clouds);
                    cloud = choice;
                    setDataGathered(true);
                }
                else {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                setDataGathered(true);
            }
        }
    }

    @Override
    public boolean execute(Game game) throws EriantysExceptions {
        if(!characterCardUsed || characterCardExecuted)
            return normalExecute(game);
        else
        if(game.getGameState() instanceof TakeCloudState)
        {
            ((TakeCloudState)game.getGameState()).setCharacterCardUsed(characterCardUsed);
            ((TakeCloudState)game.getGameState()).setCharacterIndex(characterIndex);
        }
        return true;
    }
    public boolean normalExecute(Game game) throws EriantysExceptions {
        if(!isDataGathered())
            return false;
        if(!(game.getGameState() instanceof TakeCloudState))
            return false;
        if (!getGame().isStudentFinished()) {
            Player p = game.findPlayerByName(getUsername());
            Cloud c = game.getTable().getClouds().get(cloud);
            p.getPb().addCloudToWaitingRoom(c.getStudents());
            c.emptyCloud();
            setMsg(String.format("Player %s took cloud %d", getUsername(), cloud + 1));
        }
        else
            setMsg("Last turn could result in take students from empty cloud");
        return true;
    }

    /**
     * @param inputs
     * inputs.get(0) cloud_index : int
     * @return
     * Config.GUI_COMMAND_GETDATA_SUC: if ok
     * Config.GUI_COMMAND_GETDATA_SUC: if cloud chosen is an empty cloud
     * */
    @Override
    public String GUIGetData(ArrayList<Object> inputs) {
        if (!getGame().isStudentFinished()) {
            int cloud = (int) inputs.get(0);
            if (Arrays.stream(getGame().getTable().getClouds().get(cloud).getStudents()).sum() == 0)
                return Config.GUI_EMPTY_CLOUD;
            this.cloud = cloud;
        }
        setDataGathered(true);
        return Config.GUI_COMMAND_GETDATA_SUC;
    }
}

