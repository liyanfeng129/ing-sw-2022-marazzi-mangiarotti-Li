package it.polimi.ingsw.state;

import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.SelectCloudCommand;
import it.polimi.ingsw.command.UseCharacterCommand;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.InnerExceptions;

import java.io.Serializable;

public class TakeCloudState extends State implements Serializable {
    private boolean can=false;
    private int characterIndex = -1;
    private boolean characterCardUsed;
    private boolean characterCardExecuted;

    public TakeCloudState(Game game, int phase,boolean characterCardExecuted,boolean characterCardUsed) {
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
        if(getGame().getLastCommand().execute(getGame()))
        {
            if(!characterCardUsed || characterCardExecuted) // if character has not been used or character has been executed
            {
                setCan(true);
            }
            if (getGame().getLastCommand() instanceof UseCharacterCommand)  // command executed was useCharacterCommand
            {
                characterCardExecuted = true;
                getGame().setUsedCharacter((UseCharacterCommand) getGame().getLastCommand());
            }
            /*
             * if someone used a character card in previous turn
             * undo the temporary effect caused by the card
             * reset the card
             * */
            if(getGame().getUsedCharacter() != null)
            {
                getGame().getUsedCharacter().undo(getGame());
                getGame().setUsedCharacter(null);
            }
        }
        if(canChangeState())
        {
            if (getPhase()==getGame().getN_Player()-1)
            {
                try
                {
                    getGame().getTable().initClouds();
                    if (getGame().getTurnList().get(0).getHand().getN_cards()==0) {
                        getGame().changeGameState(new EndGameState(getGame(), getPhase()));
                    }
                    else
                        getGame().changeGameState(new PlanningState(getGame(), 0));
                }
                catch (InnerExceptions.NotEnoughStudentsInBagException e)
                {
                    if(!getGame().isStudentFinished()){
                        getGame().setStudentFinished(true);
                        //System.out.println(getGame().isStudentFinished());
                        getGame().changeGameState(new PlanningState(getGame(), 0));
                    }
                    else {
                        //System.out.println(getGame().isStudentFinished());
                        getGame().changeGameState(new EndGameState(getGame(), 0));
                    }
                }
            }
            else
                getGame().changeGameState(new ActionState(getGame(),getPhase()+1));
        }
        getGame().removeCommand();
        getGame().addCommand(getGame().getGameState().generateCommand());
    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        if (!canChangeState()) {
            if (!characterCardExecuted && characterCardUsed) {
                String userName = getGame().getTurnList().get(getPhase()).getName();
                boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
                CharacterCard card = getGame().getTable().getCharacters().get(characterIndex);
                return new UseCharacterCommand(cliClient, getGame(), userName, card);
            } else {
                String userName = getGame().getTurnList().get(getPhase()).getName();
                boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
                return new SelectCloudCommand(cliClient, getGame(), userName, characterCardUsed, characterCardExecuted);
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
