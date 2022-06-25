package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.AssistantType;
import it.polimi.ingsw.model.Hand;
import it.polimi.ingsw.model.Mage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    private Hand hand;
    private Assistant a1;
    private Assistant a2;
    @BeforeEach
    public void setup(){
        hand=new Hand(Mage.MAGE1);
        a1=new Assistant(AssistantType.Card_1,Mage.MAGE1);
        a2=new Assistant(AssistantType.Card_7,Mage.MAGE1);
    }
    @AfterEach
    public void teardown(){
        hand=null;
    }
    @Test
    public void test_setList_cards(){
        hand.setList_cards(Mage.MAGE1);
        assertEquals(a2.getNum(),hand.getList_cards().get(6).getNum());
        }
    @Test
    public void test_getN_cards(){
        assertEquals(10,hand.getN_cards());
    }
    @Test
    public void test_useCard(){
        //Assistant a=new Assistant(AssistantType.Card_1,Mage.MAGE1);
        hand.use_cards(AssistantType.Card_1);
        System.out.println(hand.getList_cards());
        assertEquals(9,hand.getN_cards());
        assertEquals(9,hand.getList_cards().size());
        //assertEquals(a.getNum()+1,hand.getList_cards().get(0).getNum());

    }
    @Test
    public void test_lastPlayedCard(){
        hand.setLastPlayedCard(a1.getSteps());
        hand.setLastValueAssistant(a1.getNum());
        assertEquals(1,hand.getLastPlayedCard());
        assertEquals(1,hand.getLastValueAssistant());
    }

}