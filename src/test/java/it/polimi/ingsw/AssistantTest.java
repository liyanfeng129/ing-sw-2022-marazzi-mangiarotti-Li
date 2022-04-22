package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {
    @Test
    public void test_getNum(){
        Assistant a=new Assistant(AssistantType.Card_1,Mage.MAGE1);
        assertEquals(AssistantType.Card_1.getNum(),a.getNum());
    }
    @Test
    public void test_getSteps(){
        Assistant a=new Assistant(AssistantType.Card_1,Mage.MAGE1);
        assertEquals(AssistantType.Card_1.getSteps(),a.getSteps());
    }

}