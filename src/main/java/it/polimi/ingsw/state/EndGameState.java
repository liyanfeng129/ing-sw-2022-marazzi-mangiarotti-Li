package it.polimi.ingsw.state;

import it.polimi.ingsw.command.Command;
import it.polimi.ingsw.command.EndGameCommand;
import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.*;

public class EndGameState extends State implements Serializable {
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
        System.out.println("end game");
        //if (getGame().getLastCommand().execute(getGame())) {
            System.out.println("game ended");
            int[] players = new int[getGame().getN_Player()];
            ArrayList<String> winners = new ArrayList<>();
            Integer[] max_n_professor = new Integer[getGame().getN_Player()];
            int maxNumOfTower = 0;
            int max3 = 0;
                //calcolo vincitore se player finisce torri
                for (int i = 0; i < getGame().getN_Player(); i++) {
                    if (getGame().getTurnList().get(i).getPlayerBoard().getN_tower() == 0) {
                        winners.add(getGame().getPlayers().get(i).getName());
                    }


                    System.out.println("winner is " + winners.get(0));

                }
                //caso nessuno finisce torri,controllo chi ne ha di piu
                if (winners.size() == 0) {
                    for (int i = 0; i < getGame().getTable().getIslands().size(); i++) {
                        for (int j = 0; j < getGame().getN_Player(); j++) {
                            if (getGame().getTable().getIslands().get(i).getTower() == getGame().getPlayers().get(j).getTowerColor())
                                players[i] = players[i] + getGame().getTable().getIslands().get(i).getSize();
                        }
                    }
                    for (int i = 0; i < players.length; i++) {
                        if (players[i] >= maxNumOfTower)
                            maxNumOfTower = players[i];
                    }
                    for (int i = 0; i < players.length; i++) {
                        if (players[i] == maxNumOfTower)
                            winners.add(getGame().getPlayers().get(i).getName());
                    }
                    //caso piu giocatori con stesso numero torre
                    for (int i = 0; i < winners.size(); i++) {
                        if (getGame().findPlayerByName(winners.get(i)).getMage() == getGame().getProfessors().getList_professors()[0])
                            max_n_professor[i]++;
                        if (getGame().findPlayerByName(winners.get(i)).getMage() == getGame().getProfessors().getList_professors()[1])
                            max_n_professor[i]++;
                        if (getGame().findPlayerByName(winners.get(i)).getMage() == getGame().getProfessors().getList_professors()[2])
                            max_n_professor[i]++;
                        if (getGame().findPlayerByName(winners.get(i)).getMage() == getGame().getProfessors().getList_professors()[3])
                            max_n_professor[i]++;
                        if (getGame().findPlayerByName(winners.get(i)).getMage() == getGame().getProfessors().getList_professors()[4])
                            max_n_professor[i]++;
                    }
                    List<Integer> max_number = Arrays.asList(max_n_professor);
                    int max_prof = max_number.indexOf(Collections.max(max_number));

                    for (int i = 0; i < winners.size(); i++) {
                        if (max_n_professor[i] < max_prof)
                            winners.remove(i);
                    }
                    for (int i=0;i<winners.size();i++){
                        System.out.println("winner is " + winners.get(i));
                    }

                }

       // }

    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
        return new EndGameCommand(cliClient, getGame(), "endgame");
    }
}
