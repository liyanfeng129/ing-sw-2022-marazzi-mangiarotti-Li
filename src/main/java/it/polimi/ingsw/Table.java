package it.polimi.ingsw;

import java.util.ArrayList;

// prova yan
public class Table {
    private ArrayList<Island> Islands = new ArrayList<Island>();
    private int MN_position=0;
    private String[] characters;

    public Table(){
        for (int i=0;i<12;i++){
            Islands.add(new Island());
        }
        Islands.get(MN_position).setMotherNature(true);
        characters = this.automaticGenerateCharacters();
    }
    public void mergeIsland() throws EriantysExceptions
    {
        int leftAdjacent;
        int rightAdjacent;

        /*
        * if MN_pos is in the first position, its left adjacent island would be the last one
        * otherwise is just in its left
        * */
        if(MN_position == 0)
            leftAdjacent = Islands.size() - 1;
        else
            leftAdjacent = MN_position - 1;
        /*
         * if MN_pos is in the last position, its right adjacent island would be the first one
         * otherwise is just in its right
         * */
        if(MN_position == Islands.size() - 1)
            rightAdjacent = 0;
        else
            rightAdjacent = MN_position + 1;

        /*
        * if one of the adjacent has the same tower as the one in the motherNatureIsland
        * then the two islands will be merged
        * and the adjacent will be removed
        * */
        if(Islands.get(leftAdjacent).getTower().equals(Islands.get(MN_position).getTower()))
        {
            Islands.get(MN_position).mergeStudents(Islands.get(leftAdjacent).getStudents());
            Islands.get(MN_position).IncreasingSize(Islands.get(leftAdjacent).getSize());
            Islands.remove(leftAdjacent);
        }

        if(Islands.get(rightAdjacent).getTower().equals(Islands.get(MN_position).getTower()))
        {
            Islands.get(MN_position).mergeStudents(Islands.get(rightAdjacent).getStudents());
            Islands.get(MN_position).IncreasingSize(Islands.get(rightAdjacent).getSize());
            Islands.remove(leftAdjacent);
        }




    }
    public void move_MN(int move){
        if((MN_position+move)>=Islands.size())
            MN_position=((MN_position+move)-Islands.size()+1);
        else
            MN_position=MN_position+move;
    }
    private String[] automaticGenerateCharacters()
    {
        String characters[] = {"es1", "es2", "es3"};
        // here goes algorithm
        return characters;
    }
}

