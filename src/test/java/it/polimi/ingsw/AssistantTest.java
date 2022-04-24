package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {
    private Assistant assistant;
    @BeforeEach
    public void setup(){
        assistant=new Assistant(AssistantType.Card_1,Mage.MAGE1);
    }
    @Test
    public void test_getNum(){
        assertEquals(AssistantType.Card_1.getNum(),assistant.getNum());
    }
    @Test
    public void test_getSteps(){
        assertEquals(AssistantType.Card_1.getSteps(),assistant.getSteps());
    }

}