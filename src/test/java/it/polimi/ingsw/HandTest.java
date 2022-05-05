package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    private Hand hand;
    @BeforeEach
    public void setup(){
        //hand=new Hand();
    }
    @Test
    public void test_setList_cards(){
        Assistant a1=new Assistant(AssistantType.Card_1,Mage.MAGE1);
        Assistant a2=new Assistant(AssistantType.Card_7,Mage.MAGE1);
        hand.setList_cards(Mage.MAGE1);
        assertEquals(a2.getNum(),hand.getList_cards().get(6).getNum());
        }
    @Test
    public void test_getN_cards(){
      //  Hand hand=new Hand();
        hand.setList_cards(Mage.MAGE1);
        assertEquals(10,hand.getN_cards());
    }
    @Test
    public void test_useCard(){
        hand.setList_cards(Mage.MAGE1);
        Assistant a=new Assistant(AssistantType.Card_1,Mage.MAGE1);
        hand.use_cards(a);
        assertEquals(9,hand.getN_cards());
        //assertEquals(a.getNum()+1,hand.getList_cards().get(0).getNum());

    }


}