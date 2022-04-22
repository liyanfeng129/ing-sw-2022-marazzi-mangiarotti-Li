package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    @Test
    public void test_setList_cards(){
        Hand hand=new Hand();
        hand.setList_cards(Mage.MAGE1);
       // assertArrayEquals();
    }
    @Test
    public void test_getN_cards(){
        Hand hand=new Hand();
        //assertEquals(hand.List_Cards.size,hand.getN_cards());
    }

}