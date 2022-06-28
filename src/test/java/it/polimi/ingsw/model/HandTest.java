package it.polimi.ingsw.model;

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
        a1=null;
        a2=null;
    }

    /**
     * test for getter & setter of List_cards
     */
    @Test
    public void test_List_cards(){
        hand.setList_cards(Mage.MAGE1);
        assertEquals(a2.getNum(),hand.getList_cards().get(6).getNum());
    }

    /**
     * test for getter of N_Cards
     */
    @Test
    public void test_getN_cards(){
        assertEquals(10,hand.getN_cards());
    }

    /**
     * test for method useCard
     */
    @Test
    public void test_useCard(){
        hand.use_cards(AssistantType.Card_1);
        assertEquals(9,hand.getN_cards());

    }

    /**
     * test for getter & setter of the last played card(steps and value)
     */
    @Test
    public void test_lastPlayedCard(){
        hand.setLastStepsAssistant(a1.getSteps());
        hand.setLastValueAssistant(a1.getNum());
        assertEquals(1,hand.getLastStepsAssistant());
        assertEquals(1,hand.getLastValueAssistant());
    }

}