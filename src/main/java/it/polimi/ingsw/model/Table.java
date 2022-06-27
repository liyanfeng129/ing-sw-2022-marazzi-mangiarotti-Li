package it.polimi.ingsw.model;

import it.polimi.ingsw.characterCards2.CharacterCard;

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

    /**
     * constructor for class Table
     * initialize 12 island and set Mother Nature on first island
     * initialize bag
     * initialize characters card effects
     */
    public Table() {
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

    /**
     * call a method to initialize characters card and clouds
     * @param clouds
     * @param characterCards
     * @throws EriantysExceptions if initIsland or initClouds throw an exception
     */
    public void tableInit(ArrayList<Cloud> clouds, ArrayList<CharacterCard> characterCards) throws EriantysExceptions {
        this.clouds = clouds;
        this.characterCards = characterCards;
        initIslands();
        initClouds();
    }

    /**
     * fill a cloud with random student from bag
     * @throws EriantysExceptions if there are not enough student left
     */
    public void initClouds() throws EriantysExceptions {
        for(int i = 0; i<clouds.size(); i++)
            clouds.get(i).setCloudStudents(bag.draw(clouds.get(i).getSize()));
    }

    /**
     * initialize the island for the start of the game
     * add one random student each island except island 0 and 6
     * @throws EriantysExceptions if there are not enough students
     */
    private void initIslands() throws EriantysExceptions {
        int[] students = bag.bagSet1();
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



    public Island getIsland(int island_pos){
        return Islands.get(island_pos);
    }

    /**
     * this method check if left and right islands of current island have same color of tower on them
     * if one or both of them have same color of the current island, delete them and increase size
     *  and students of current island
     * @throws EriantysExceptions if there is no Mother Nature
     */
    public void mergeIsland() throws EriantysExceptions
    {
        //check leftIsland
        if(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getTowerColor()!=null) {
            if (Islands.get(getMotherNatureIndex()).getTowerColor().equals(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getTowerColor())) {
                Islands.get(getMotherNatureIndex()).mergeStudents(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getStudents());
                Islands.get(getMotherNatureIndex()).IncreasingSize(Islands.get(getRightIslandIndex(getMotherNatureIndex())).getSize());
                Islands.remove(getRightIslandIndex(getMotherNatureIndex()));

            }
        }
        //check rightIsland
        if(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getTowerColor()!=null) {
            if (Islands.get(getMotherNatureIndex()).getTowerColor().equals(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getTowerColor())) {
                Islands.get(getMotherNatureIndex()).mergeStudents(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getStudents());
                Islands.get(getMotherNatureIndex()).IncreasingSize(Islands.get(getLeftIslandIndex(getMotherNatureIndex())).getSize());
                Islands.remove(getLeftIslandIndex(getMotherNatureIndex()));
            }
        }
    }

    /**
     * this method move Mother Nature on another island
     * @param move number of steps to the right that Mother Nature has to make
     * @throws EriantysExceptions if there is no Mother Nature
     */
    public void moveMotherNature(int move) throws EriantysExceptions {
        int newMn = getMotherNatureIndex();
        for(int i = 0; i < move; i ++ )
            newMn = getRightIslandIndex(newMn);
        Islands.get(getMotherNatureIndex()).setMotherNature(false);
        Islands.get(newMn).setMotherNature(true);
        //mergeIsland();
    }

    /**
     * search among all islands where is Mother Nature
     * @return index of island that has Mother Nature
     * @throws EriantysExceptions if there is no Mother Nature
     */
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

    /**
     * return index of left island respect the parameter given
     * @param index island position
     * @return index of left island
     */
    private int getLeftIslandIndex(int index)
    {
        if(index == 0)
            return Islands.size() - 1;
        else
            return index - 1;
    }

    /**
     * return index of right island respect the parameter given
     * @param index island position
     * @return index of right island
     */
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

    /**
     * set if character card 6 is being used
     * @param card6 true if a player use this character
     */
    public void setCard6(boolean card6) {
        this.card6 = card6;
    }
    public String getCard8() {
        return card8;
    }

    /**
     * set if character card 8 is being used
     * @param card8 name of player who use this character
     */
    public void setCard8(String card8) {
        this.card8 = card8;
    }

    /**
     * set if character card 9 is being used
     * @param card9 color of student to ignore computing influence
     */
    public void setCard9(int card9) {
        this.card9 = card9;
    }

    public int getCard9() {
        return card9;
    }

    /**
     * compute the influence (student and tower) of every player on the island with Mother Nature
     * @param game game where to compute influence
     * @param prof list of professors and who they belong to
     * @return an array where each position is the influence of the corresponding player
     * @throws EriantysExceptions if there is no Mother Nature
     */
    public int[] getInfluence(Game game, Professors prof) throws EriantysExceptions {
        int[] influence= new int[3];
        for (int i = 0;i<5; i++) {
            if (game.getTable().getCard9() != i) {
                if (prof.getList_professors()[i] == Mage.MAGE1)
                    influence[0] = influence[0] + game.getTable().getIsland(getMotherNatureIndex()).getStudents()[i];
                if (prof.getList_professors()[i] == Mage.MAGE2)
                    influence[1] = influence[1] + game.getTable().getIsland(getMotherNatureIndex()).getStudents()[i];
                if (prof.getList_professors()[i] == Mage.MAGE3)
                    influence[2] = influence[2] + game.getTable().getIsland(getMotherNatureIndex()).getStudents()[i];
            }
        }
        if(!game.getTable().isCard6()) {
            for (int i = 0; i < game.getN_Player(); i++) {
                if (game.getTable().getIsland(getMotherNatureIndex()).getTowerColor() == game.getPlayers().get(i).getTowerColor()) {
                    influence[i] = influence[i] + game.getTable().getIsland(getMotherNatureIndex()).getSize();
                }
            }
        }
        return influence;
    }

    /**
     * compute the player who has higher influence and eventually set a tower on the island with
     * Mother Nature
     * @param game game where to compute influence
     * @return player who has max influence
     * @throws EriantysExceptions if there is no Mother Nature
     */
    public Player getPlayerMaxInfluence(Game game) throws EriantysExceptions {
        int[] influence  = game.getTable().getInfluence(game, game.getProfessors());
        if (game.getTable().getCard8()!=null){
            for (int i=0;i<game.getN_Player();i++){
                if (game.getTable().getCard8().equals(game.getPlayers().get(i).getName()))
                    influence[i]=influence[i]+2;
            }
        }
        int max_index=0;
        int max =0;
        for (int i=0; i<game.getN_Player();i++) {
            if (max < influence[i])
            {
                max = influence[i];
                max_index = i;
            }
        }

        for (int i=0; i<game.getN_Player();i++) {
            if (max ==influence[i] && max_index!=i){
                return null;
            }


        }
        TowerColor color= game.getPlayers().get(max_index).getTowerColor();
        int MN_pos=game.getTable().getMotherNatureIndex();
        int MN_island_size=game.getTable().getIsland(MN_pos).getSize();
        TowerColor Previous_TC = game.getTable().getIsland(MN_pos).getTowerColor();
        if (Previous_TC != null ){
            for (int i=0;i<game.getN_Player();i++){
                if (game.getPlayers().get(i).getTowerColor()==Previous_TC)
                    game.getPlayers().get(i).getPb().moveTower(+MN_island_size);
            }
        }
        game.getTable().getIsland(MN_pos).setTowerColor(color);
        game.getPlayers().get(max_index).getPb().moveTower(-MN_island_size);
        return   game.getPlayers().get(max_index);

    }

    /**
     * find a character
     * @param name name of the character to be found
     * @return the character card with the name in parameter
     * @throws EriantysExceptions if there is not a character card with this name in table
     */
    public CharacterCard findCharacterCardByName(String name) throws EriantysExceptions
    {
        for(int i = 0; i < characterCards.size(); i++)
            if(characterCards.get(i).name().equals(name))
                return characterCards.get(i);
        throw new InnerExceptions.CharacterCardError("Character "+ name +" not find" );
    }




    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public void addCharacterCards(CharacterCard characterCards) {
        this.characterCards.add(characterCards);
    }
}

