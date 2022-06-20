package it.polimi.ingsw;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.AssistantType;
import it.polimi.ingsw.model.Mage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {
    private Assistant assistant;
    @BeforeEach
    public void setup(){
        assistant=new Assistant(AssistantType.Card_1, Mage.MAGE1);
    }
    @Test
    public void test_getNum(){
        assertEquals(1,assistant.getNum());
    }
    @Test
    public void test_getSteps(){
        assertEquals(1,assistant.getSteps());
    }
    @Test
    public void test_Mage(){
        assertEquals(Mage.MAGE1,assistant.getMage());
    }
}