package it.polimi.ingsw.cestino.controller;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class ActionPhase1 implements Phase{
    @Override
    public void next(Turn turn, Game game) throws EriantysExceptions {
        ArrayList<Object> response = new ArrayList();
        ArrayList<Object> msg_list = new ArrayList();
        int student;

        int ClouSize = game.getTable().getClouds().get(0).getSize();

        for (int i=0;i<ClouSize;i++){
            //mando messaggio richiesta studente
            //ricevo messaggio msg_list =
            Player player = (Player) msg_list.get(1);
            student=(Integer) msg_list.get(2);
            player.getPlayerBoard().takeStudentFromWaitingRoom(student);
            //mando messaggio dove mandarlo
            //ricevo messaggio msg_list=
            if((String)msg_list.get(0)=="ISLAND")
                game.getTable().getIslands().get((Integer) msg_list.get(2)).addStudent(student);
            else {
                player.getPlayerBoard().addStudentToHolder(student);
            }
        }
        turn.setState(new ActionPhase2());
    }


    @Override
    public void printStatus() {

    }

}
