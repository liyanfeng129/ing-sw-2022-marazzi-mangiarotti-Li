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
    }

    @Override
    public Command generateCommand() throws EriantysExceptions {
        boolean cliClient = getGame().getTurnList().get(getPhase()).isCliClient();
        //if (getGame().getLastCommand().execute(getGame())) {

        int[] players = new int[getGame().getN_Player()];
        //System.out.println(getGame().getPlayers().get(1).getName());
        ArrayList<String> winners = new ArrayList<>();
        Integer[] max_n_professor;
        int maxNumOfTower = 0;
        int max3 = 0;
        //calcolo vincitore se player finisce torri
        for (int i = 0; i < getGame().getN_Player(); i++) {
            if (getGame().getPlayers().get(i).getPb().getN_tower() == 0)
                winners.add(getGame().getPlayers().get(i).getName());
        }


        System.out.println(" " + winners.size());

        //caso nessuno finisce torri,controllo chi ne ha di piu
        if (winners.size() == 0) {
            int min=getGame().getPlayers().get(0).getPb().getN_tower();
            for (int i = 0; i < getGame().getN_Player(); i++) {
                    if (getGame().getPlayers().get(i).getPb().getN_tower()<=min)
                        min=getGame().getPlayers().get(i).getPb().getN_tower();
            }
            for (int i = 0; i < getGame().getN_Player(); i++) {
                if (getGame().getPlayers().get(i).getPb().getN_tower()==min)
                    winners.add(getGame().getPlayers().get(i).getName());
            }
            if (winners.size() >1) {
                max_n_professor=new Integer[winners.size()];
                for (int i=0;i<winners.size();i++){
                    max_n_professor[i]=0;
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
                /*int max_prof=max_n_professor[0];
                for (int i=0;i<winners.size();i++){
                    if(max_n_professor[i]>max_prof)
                        max_prof=max_n_professor[i];
                }
                */

                //funzione figa ma non funzionante
                List<Integer> max_number = Arrays.asList(max_n_professor);
                int max_prof = max_number.indexOf(Collections.max(max_number));

                for (int i = 0; i < winners.size(); i++) {
                    if (max_n_professor[i] < max_prof)
                        winners.remove(i);
                }
            }
           // for (int i=0;i<winners.size();i++){
            //    System.out.println("winner is " + winners.get(i));
            //}

        }
        for (int i=0;i<winners.size();i++){
            System.out.println("winner is " + winners.get(i));
        }

        // }
        return new EndGameCommand(cliClient, getGame(),winners, "endgame");
    }
}
