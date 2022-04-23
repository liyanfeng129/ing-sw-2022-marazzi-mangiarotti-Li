package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    @Test
    public void test_setList_cards(){
        Hand hand1=new Hand();
        Hand hand2=new Hand();
        hand1.setList_cards(Mage.MAGE1);
        hand2.setList_cards(Mage.MAGE1);
            assertArrayEquals(hand2.getList_cards().toArray(),hand1.getList_cards().toArray());
        }
    @Test
    public void test_getN_cards(){
        Hand hand=new Hand();
        //assertEquals(hand.List_Cards.size,hand.getN_cards());
    }

}