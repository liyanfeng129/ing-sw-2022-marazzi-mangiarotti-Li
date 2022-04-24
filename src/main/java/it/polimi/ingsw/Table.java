package it.polimi.ingsw;

import java.util.ArrayList;

// prova yan
public class Table {
    private ArrayList<Island> Islands = new ArrayList<Island>();
    private String[] characters;

    public Table(){
        for (int i=0;i<12;i++){
            Islands.add(new Island());
        }
        Islands.get(0).setMotherNature(true);
        characters = this.automaticGenerateCharacters();
    }

    public ArrayList<Island> getIslands() {
        return Islands;
    }

    public Island getIslands(int isola){
        return Islands.get(isola);
    }
    public void mergeIsland() throws EriantysExceptions
    {
        //check leftIsland
        if(Islands.get(getMotherNatureIndex()).getTower().equals(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getTower()))
        {
            Islands.get(getMotherNatureIndex()).mergeStudents(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getStudents());
            Islands.get(getMotherNatureIndex()).IncreasingSize(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getSize());
            Islands.remove(getRightIslandIndex(getMotherNatureIndex()));

        }
        //check rightIsland
        if(Islands.get(getMotherNatureIndex()).getTower().equals(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getTower()))
        {
            Islands.get(getMotherNatureIndex()).mergeStudents(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getStudents());
            Islands.get(getMotherNatureIndex()).IncreasingSize(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getSize());
            Islands.remove(getLeftIslandIndex(getMotherNatureIndex()));
        }
    }
    public void moveMotherNature(int move) throws EriantysExceptions {
        int newMn = getMotherNatureIndex();
        for(int i = 0; i < move; i ++ )
            newMn = getRightIslandIndex(newMn);
        Islands.get(getMotherNatureIndex()).setMotherNature(false);
        Islands.get(newMn).setMotherNature(true);
    }
    private String[] automaticGenerateCharacters()
    {
        String characters[] = {"es1", "es2", "es3"};
        // here goes algorithm
        return characters;
    }
    private int getMotherNatureIndex() throws EriantysExceptions // va messo come attributo della classe così è troppo complicato
    {
        int i = 0;
        while(i < Islands.size()){ // non ha senso questo qui c'è un problema
            if(Islands.get(i).getMotherNature())
                return i;
            else
                i++;
        }
        throw new InnerExceptions.NoMotherNatureException("Mother nature does not exist!");
    }

    private int getLeftIslandIndex(int index)
    {
        if(index == 0)
            return Islands.size() - 1;
        else
            return index - 1;
    }

    private int getRightIslandIndex(int index)
    {
        if(index == Islands.size() - 1)
            return 0;
        else
            return index + 1;
    }

}

