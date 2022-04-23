package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Test
    public void test_mergeIsland(){
        Table table=new Table();
        try {
            table.mergeIsland();
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

}