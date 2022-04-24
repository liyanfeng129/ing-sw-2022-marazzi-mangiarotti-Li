package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    private Hand hand;
    @BeforeEach
    public void setup(){
        hand=new Hand();
    }
    @Test
    public void test_setList_cards(){
        Hand hand2=new Hand();
        hand.setList_cards(Mage.MAGE1);
        hand2.setList_cards(Mage.MAGE1);
            assertArrayEquals(hand2.getList_cards().toArray(),hand.getList_cards().toArray());
        }
    @Test
    public void test_getN_cards(){
        Hand hand=new Hand();
        hand.setList_cards(Mage.MAGE1);
        assertEquals(10,hand.getN_cards());
    }


}