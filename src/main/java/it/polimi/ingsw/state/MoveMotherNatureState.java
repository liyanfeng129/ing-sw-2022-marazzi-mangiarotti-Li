package it.polimi.ingsw.state;

import it.polimi.ingsw.characterCards2.Character5;
import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.MoveMotherNatureCommand;
import it.polimi.ingsw.command.UseCharacterCommand;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.InnerExceptions;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public class MoveMotherNatureState extends State implements Serializable {
    private int maxSteps;
    private boolean can=false;
    private boolean GameEnded=false;
    private int characterIndex = -1;
    private boolean characterCardUsed;
    private boolean characterCardExecuted;
    public MoveMotherNatureState(Game game, int phase,boolean characterCardExecuted,boolean characterCardUsed) {
        super(game, phase);
        this.characterCardUsed = characterCardUsed;
        this.characterCardExecuted = characterCardExecuted;
    }

    @Override
    public void nextState() throws EriantysExceptions {

    }

    @Override
    public boolean canChangeState() throws EriantysExceptions {
        if(isCan())
            return true;
        else
            return false;
    }

    @Override
    public void executeCommand() throws EriantysExceptions {
        if(!getGame().getLastCommand().execute(getGame()))
            throw new InnerExceptions.NoMotherNatureException("Cannot execute command in moveMotherNatureState.");
        int MN_pos=getGame().getTable().getMotherNatureIndex();
        if(!characterCardUsed || characterCardExecuted) // if character has not been used or character has been executed
        {
            // means that command executed was moveMotherNatureCommand
            if(!getGame().getTable().getIsland(MN_pos).isNoEntryTiles()) {
                Player player = getGame().getTable().getPlayerMaxInfluence(getGame());
                //condizione endGame finite torri in pb
                if (player != null) {
                    for (int i = 0; i < getGame().getN_Player(); i++) {
                        if (getGame().getPlayers().get(i).getPb().getN_tower() <= 0) {
                            setGameEnded(true);
                        }
                    }
                    getGame().getTable().mergeIsland();
                    if (getGame().getTable().getIslands().size() <= 3) {
                        setGameEnded(true);
                    }
                }
            }
            else{
                getGame().getTable().getIsland(MN_pos).setNoEntryTiles(false);
                //devo aggiungere la no entrytiles alla carta 5
                Character5 card5 = (Character5) getGame().getTable().findCharacterCardByName("Character5");
                card5.takeEntryTile();
            }
            setCan(true);
        }
        if (getGame().getLastCommand() instanceof UseCharacterCommand)  // command executed was useCharacterCommand
        {
            characterCardExecuted = true;
            getGame().setUsedCharacter((UseCharacterCommand) getGame().getLastCommand());
        }
        if (canChangeState()) {
            if (!isGameEnded()) {
                getGame().changeGameState(new TakeCloudState(getGame(), getPhase()));
            }
            else{
                getGame().changeGameState(new EndGameState(getGame(), getPhase()));
            }
        }
        getGame().removeCommand();
        getGame().addCommand(getGame().getGameState().generateCommand());
    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        if(!canChangeState())
        {
            if(!characterCardExecuted && characterCardUsed)
            {
                String userName = getGame().getTurnList().get(getPhase()).getName();
                boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
                CharacterCard card = getGame().getTable().getCharacters().get(characterIndex);
                return new UseCharacterCommand(cliClient,getGame(),userName,card);
            }
            else
            {
                int maxSteps = getGame().getTurnList().get(getPhase()).getHand().getLastStepsAssistant();
                String userName = getGame().getTurnList().get(getPhase()).getName();
                boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
                return new MoveMotherNatureCommand(cliClient,getGame(),userName,maxSteps,characterCardUsed, characterCardExecuted);
            }
        }
        throw new InnerExceptions.PlanningStateError("cannot generate command.");
    }

    public boolean isCan() {
        return can;
    }

    public void setCan(boolean can) {
        this.can = can;
    }

    public boolean isGameEnded() {
        return GameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        GameEnded = gameEnded;
    }
    public boolean isCharacterCardUsed() {
        return characterCardUsed;
    }

    public void setCharacterCardUsed(boolean characterCardUsed) {
        this.characterCardUsed = characterCardUsed;
    }
    public int getCharacterIndex() {
        return characterIndex;
    }

    public void setCharacterIndex(int characterIndex) {
        this.characterIndex = characterIndex;
    }
}


