package it.polimi.ingsw;

import it.polimi.Island;

import java.util.ArrayList;
import java.util.LinkedList;

// prova/
public class Table {
    private ArrayList<Island> Islands = new ArrayList<Island>();
    private int MN_position=0;
    private String[] characther;
    public Table(){
        for (int i=0;i<12;i++){
            Islands.add(new Island());
        }
    }
    public void mergeIsland(){


    }
    public void move_MN(int move){
        if((MN_position+move)>=Islands.size())
            MN_position=((MN_position+move)-Islands.size()+1);
        else
            MN_position=MN_position+move;
    }
}

