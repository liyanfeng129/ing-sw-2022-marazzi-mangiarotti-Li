package it.polimi.ingsw.state;

import it.polimi.ingsw.characterCards2.CharacterCard;
import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.MoveStudentFromWaitingRoomCommand;
import it.polimi.ingsw.command.UseCharacterCommand;
import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.Arrays;

public class ActionState extends State implements Serializable {

    private int numStudents;
    private boolean onIsland;
    private boolean expertMode;
    private boolean characterCardUsed;
    private int characterIndex = -1;
    private boolean characterCardExecuted;
    public ActionState(Game game, int phase) {
        super(game,phase);
        characterCardUsed = false;
        characterCardExecuted = false;
        numStudents = getGame().getTable().getClouds().get(0).getSize();
        expertMode = getGame().isExpertMode();
    }

    @Override
    public void nextState() throws EriantysExceptions {

    }

    @Override
    public boolean canChangeState() throws EriantysExceptions {
        boolean can = false;
        PlayerBoard pb = getGame().getTurnList().get(getPhase()).getPlayerBoard();
        if(Arrays.stream(pb.getWaitingRoom()).sum() == pb.getMaxStudentsInWaiting() - numStudents )
            can = true;
        return can;
    }

    @Override
    public void executeCommand() throws EriantysExceptions {
        if(getGame().getLastCommand().execute(getGame()))
        {
            if( (!characterCardUsed || characterCardExecuted) && !onIsland)
            {
                getGame().getProfessors().assignProfessor(getGame().getPlayers());
            }
            if(getGame().getLastCommand() instanceof UseCharacterCommand)
            {
                characterCardExecuted = true;
                getGame().setUsedCharacter((UseCharacterCommand) getGame().getLastCommand());
            }
        }
        if(canChangeState())
        {
            getGame().changeGameState(new MoveMotherNatureState(getGame(), getPhase()));
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
                int[] waitingRoom = getGame().getTurnList().get(getPhase()).getPlayerBoard().getWaitingRoom();
                String userName = getGame().getTurnList().get(getPhase()).getName();
                boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
                return new MoveStudentFromWaitingRoomCommand(waitingRoom,cliClient,getGame(),userName,characterCardUsed,characterCardExecuted);
            }
        }
        throw new InnerExceptions.PlanningStateError("cannot generate command.");
    }

    public boolean isOnIsland() {
        return onIsland;
    }

    public void setOnIsland(boolean onIsland) {
        this.onIsland = onIsland;
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
