package it.polimi.ingsw.model;

import it.polimi.ingsw.characterCards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Table implements Serializable {
    private ArrayList<Island> Islands;
    private ArrayList<Cloud> clouds;
    private ArrayList<CharacterCard> characterCards;
    private Bag bag;
    private boolean card6;
    private String card8;
    private int card9;

    public Table() throws EriantysExceptions {
        Islands = new ArrayList<>();
        for (int i=0;i<12;i++){
            Islands.add(new Island());
        }
        clouds = new ArrayList<>();
        characterCards = new ArrayList<>();
        Islands.get(0).setMotherNature(true);
        this.bag = new Bag();
        card6=false;
        card8=null;
        card9=-1;
        //initIslands();
    }

    public void tableInit(ArrayList<Cloud> clouds, ArrayList<CharacterCard> characterCards) throws EriantysExceptions {
        this.clouds = clouds;
        this.characterCards = characterCards;
        initIslands();
        initClouds();
    }

    private void initClouds() throws EriantysExceptions {
        for(int i = 0; i<clouds.size(); i++)
            clouds.get(i).setStudents(bag.draw(clouds.get(i).getSize()));
    }


    private void initIslands() throws EriantysExceptions {
        int students[] = bag.bagSet1();
        Random rand = new Random();
        // Obtain a number between [0 - 4].
        for (int i=1; i<12 ;i++)
        {
            if(i != 6) // skip this island
            {
                int random = rand.nextInt(5);
                while (students[random] == 0)
                    random = rand.nextInt(5);
                students[random] --;
                Islands.get(i).addStudent(random);
            }
        }
    }

    public ArrayList<Island> getIslands() {
        return this.Islands;
    }
    public ArrayList<Cloud> getClouds() {
        return this.clouds;
    }
    public ArrayList<CharacterCard> getCharacters() {
        return this.characterCards;
    }



    public Island getIslands(int island_pos){
        return Islands.get(island_pos);
    }

    // forse meglio privato
    public void mergeIsland() throws EriantysExceptions
    {
        //check leftIsland
        if(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getTower()!=null) {
            if (Islands.get(getMotherNatureIndex()).getTower().equals(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getTower())) {
                Islands.get(getMotherNatureIndex()).mergeStudents(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getStudents());
                Islands.get(getMotherNatureIndex()).IncreasingSize(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getSize());
                Islands.remove(getRightIslandIndex(getMotherNatureIndex()));

            }
        }
        //check rightIsland
        if(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getTower()!=null) {
            if (Islands.get(getMotherNatureIndex()).getTower().equals(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getTower())) {
                Islands.get(getMotherNatureIndex()).mergeStudents(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getStudents());
                Islands.get(getMotherNatureIndex()).IncreasingSize(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getSize());
                Islands.remove(getLeftIslandIndex(getMotherNatureIndex()));
            }
        }
    }
    public void moveMotherNature(int move) throws EriantysExceptions {
        int newMn = getMotherNatureIndex();
        for(int i = 0; i < move; i ++ )
            newMn = getRightIslandIndex(newMn);
        Islands.get(getMotherNatureIndex()).setMotherNature(false);
        Islands.get(newMn).setMotherNature(true);
        //mergeIsland();
    }

    public int getMotherNatureIndex() throws EriantysExceptions
    {
        int i = 0;
        while(i < Islands.size()){
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

    public Bag getBag() {
        return bag;
    }

    public boolean isCard6() {
        return card6;
    }

    public void setCard6(boolean card6) {
        this.card6 = card6;
    }
    public String getCard8() {
        return card8;
    }

    public void setCard8(String card8) {
        this.card8 = card8;
    }

    public void setCard9(int card9) {
        this.card9 = card9;
    }

    public int getCard9() {
        return card9;
    }

    public int[] getInfluence(Game game, Professors prof) throws EriantysExceptions {
        int[] influence= new int[4];
        for (int i = 0;i<5; i++) {
            if (game.getTable().getCard9() != i) {
                if (prof.getList_professors()[i] == Mage.MAGE1)
                    influence[0] = influence[0] + game.getTable().getIslands(getMotherNatureIndex()).getStudents()[i];
                if (prof.getList_professors()[i] == Mage.MAGE2)
                    influence[1] = influence[1] + game.getTable().getIslands(getMotherNatureIndex()).getStudents()[i];
                if (prof.getList_professors()[i] == Mage.MAGE3)
                    influence[2] = influence[2] + game.getTable().getIslands(getMotherNatureIndex()).getStudents()[i];
                if (prof.getList_professors()[i] == Mage.MAGE4)
                    influence[3] = influence[3] + game.getTable().getIslands(getMotherNatureIndex()).getStudents()[i];
            }
        }
        if(game.getTable().isCard6()==false) {
            for (int i = 0; i < game.getN_Player(); i++) {
                if (game.getTable().getIslands(getMotherNatureIndex()).getTower() == game.getPlayers().get(i).getTowerColor()) {
                    influence[i] = influence[i] + game.getTable().getIslands(getMotherNatureIndex()).getSize();
                }
            }
        }
        return influence;
    }

    public Player getPlayerMaxInfluence(Game game) throws EriantysExceptions {
        int[] influence  = game.getTable().getInfluence(game, game.getProfessors());
        if (game.getTable().getCard8()!=null){
            for (int i=0;i<game.getN_Player();i++){
                if (game.getTable().getCard8()==game.getPlayers().get(i).getName())
                    influence[i]=influence[i]+2;
            }
        }
        int max_index=0;
        int max =0;
        for (int i=0; i<game.getN_Player();i++) {
            if (max < influence[i])
                max = influence[i];
                max_index = i;
        }

        for (int i=0; i<game.getN_Player();i++) {
            if (max ==influence[i] && max_index!=i){
                return null;
            }


        }
        return   game.getPlayers().get(max_index);

    }




    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

