package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.EndGameCommand;
import it.polimi.ingsw.command.SelectCloudCommand;
import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.InnerExceptions;
import it.polimi.ingsw.model.TowerColor;

import java.io.Serializable;

public class EndGameState extends State implements Serializable {
    private String winner=null;
    public EndGameState(Game game, int phase) {
        super(game, phase);
    }

    @Override
    public void nextState() throws EriantysExceptions {

    }

    @Override
    public boolean canChangeState() throws EriantysExceptions {
        return false;
    }

    @Override
    public void executeCommand() throws EriantysExceptions {

    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        int player1=0;
        int player2=0;
        int player3=0;
        TowerColor color_p1=getGame().getPlayers().get(0).getTowerColor();
        TowerColor color_p2=getGame().getPlayers().get(1).getTowerColor();
        TowerColor color_p3=getGame().getPlayers().get(2).getTowerColor();
        int max=0;
        if(!canChangeState()) {
            //calcolo vincitore
            for (int i=0;i<getGame().getN_Player();i++) {
                if (getGame().getTurnList().get(i).getPlayerBoard().getN_tower() == 0) {
                    winner = getGame().getPlayers().get(i).getName();
                }
            }
            if (winner==null){
                for(int i=0;i<getGame().getTable().getIslands().size();i++){
                    if(getGame().getTable().getIslands().get(i).getTower()==color_p1)
                        player1=player1+getGame().getTable().getIslands().get(i).getSize();
                    if(getGame().getTable().getIslands().get(i).getTower()==color_p2)
                        player2=player2+getGame().getTable().getIslands().get(i).getSize();
                    if(getGame().getTable().getIslands().get(i).getTower()==color_p3)
                        player3=player3+getGame().getTable().getIslands().get(i).getSize();
                }
                if(player1>player2 && player1>player3)
                    winner=getGame().getPlayers().get(0).getName();
                else{
                    if (player2>player3)
                        winner=getGame().getPlayers().get(1).getName();
                    else
                        winner=getGame().getPlayers().get(2).getName();
                    }
                }
//devo controllare caso parita numero di torri
            boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
            return new EndGameCommand(cliClient,getGame(),"endgame",winner);
        }
        throw new InnerExceptions.PlanningSteteError("cannot generate command.");
    }
}
