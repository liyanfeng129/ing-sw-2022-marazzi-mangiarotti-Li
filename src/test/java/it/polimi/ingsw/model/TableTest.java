package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private Table table;

    /**
     * set up for class TableTest
     */
    @BeforeEach
    public void setup(){
        table=new Table();
    }

    /**
     * test for method merge island
     */
    @Test
    public void test_mergeIsland(){
        int temp[]=table.getIsland(0).getStudents();
        assertTrue(table.getIsland(0).getMotherNature());
        table.getIsland(0).setTowerColor(TowerColor.WHITE);
        table.getIsland(1).setTowerColor(TowerColor.WHITE);
        table.getIsland(11).setTowerColor(TowerColor.WHITE);
        table.getIsland(0).addStudent(3);
        temp[3]++;
        table.getIsland(11).addStudent(0);
        temp[0]++;
        try {
            table.mergeIsland();
        } catch (EriantysExceptions e) {
            fail();
        }
        assertEquals(10,table.getIslands().size());
        assertArrayEquals(temp,table.getIsland(0).getStudents());

    }

    /**
     * test for moving MotherNature
     */
    @Test
    public void test_moveMn(){
        try {
            table.moveMotherNature(3);
        } catch (EriantysExceptions e) {
            fail();
        }
        assertTrue(table.getIsland(3).getMotherNature());
        assertFalse(table.getIsland(0).getMotherNature());
    }

    /**
     * test for MN_index_exception
     * if Mother Nature is not in any island
     */
    @Test
    public void test_getMN_index_exception(){
        table.getIsland(0).setMotherNature(false);
        try {
            table.moveMotherNature(3);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

    /**
     * test for method getInfluence && getMaxPlayerInfluence
     * @throws EriantysExceptions
     */
    @Test
    public void test_influence() throws EriantysExceptions {

        Player p1=new Player("ale", Mage.MAGE1,TowerColor.WHITE,2,true);
        Player p2=new Player("leo",Mage.MAGE2,TowerColor.BLACK,2,true);
        Game game = new Game(2,false,p1);
        game.addPlayers(p2);
        game.getTable().getIsland(0).setMotherNature(true);
        Professors prof=new Professors();
        prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.NO_MAGE, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE});
        game.getTable().getIsland(0).setTowerColor(TowerColor.WHITE);
        game.getTable().getIsland(0).addStudent(0);
        game.getTable().getIsland(0).addStudent(0);
        game.getTable().getIsland(0).addStudent(1);
        game.getTable().getIsland(0).addStudent(2);
        assertArrayEquals(new int[]{3, 1,0}, game.getTable().getInfluence(game,prof));
        assertEquals(p1,game.getTable().getPlayerMaxInfluence(game));
    }

    /**
     * test for method getInfluence && getMaxPlayerInfluence when charcter8 is used
     * @throws EriantysExceptions
     */
    @Test
    public void test_influenceCard8() throws EriantysExceptions {
        Player p1=new Player("ale", Mage.MAGE1,TowerColor.WHITE,2,true);
        Player p2=new Player("leo",Mage.MAGE2,TowerColor.BLACK,2,true);
        Game game = new Game(2,true,p1);
        game.addPlayers(p2);
        game.getTable().getIsland(0).setMotherNature(true);
        Professors prof=new Professors();
        prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.NO_MAGE, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE});
        game.getTable().getIsland(0).setTowerColor(TowerColor.WHITE);
        p1.getPb().moveTower(-1);
        game.getTable().getIsland(0).addStudent(0);
        game.getTable().getIsland(0).addStudent(1);
        game.getTable().getIsland(0).addStudent(2);
        assertArrayEquals(new int[]{2, 1,0}, game.getTable().getInfluence(game,prof));
        game.getTable().setCard8(p2.getName());
        assertEquals(p2,game.getTable().getPlayerMaxInfluence(game));
        assertEquals(TowerColor.BLACK,p2.getTowerColor());
        assertEquals(8,p1.getPb().getN_tower());
        assertEquals(7,p2.getPb().getN_tower());
    }

    /**
     * test for method getInfluence && getMaxPlayerInfluence when charcter6 is used
     * @throws EriantysExceptions
     */
    @Test
    public void test_influenceCard6() throws EriantysExceptions {
        Player p1=new Player("ale", Mage.MAGE1,TowerColor.WHITE,2,true);
        Player p2=new Player("leo",Mage.MAGE2,TowerColor.BLACK,2,true);
        Game game = new Game(2,true,p1);
        game.addPlayers(p2);
        game.getTable().getIsland(0).setMotherNature(true);
        game.getTable().getIsland(0).setTowerColor(TowerColor.WHITE);
        Professors prof=new Professors();
        prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.NO_MAGE, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE});
        game.getTable().setCard6(true);
        game.getTable().getIsland(0).addStudent(0);
        game.getTable().getIsland(0).addStudent(1);
        game.getTable().getIsland(0).addStudent(2);
        assertArrayEquals(new int[]{1, 1,0}, game.getTable().getInfluence(game,prof));
    }
    /**
     * test for method getInfluence && getMaxPlayerInfluence when charcter9 is used
     * @throws EriantysExceptions
     */
    @Test
    public void test_influenceCard9() throws EriantysExceptions {
        Player p1=new Player("ale", Mage.MAGE1,TowerColor.WHITE,2,true);
        Player p2=new Player("leo",Mage.MAGE2,TowerColor.BLACK,2,true);
        Game game = new Game(2,true,p1);
        game.addPlayers(p2);
        game.getTable().getIsland(0).setMotherNature(true);
        game.getTable().getIsland(0).setTowerColor(TowerColor.WHITE);
        Professors prof=new Professors();
        prof.setList_professors(new Mage[]{Mage.MAGE1, Mage.NO_MAGE, Mage.MAGE2, Mage.MAGE2, Mage.NO_MAGE});
        game.getTable().setCard9(0);
        game.getTable().getIsland(0).addStudent(0);
        game.getTable().getIsland(0).addStudent(1);
        game.getTable().getIsland(0).addStudent(2);
        assertArrayEquals(new int[]{1, 1,0}, game.getTable().getInfluence(game,prof));
    }
}