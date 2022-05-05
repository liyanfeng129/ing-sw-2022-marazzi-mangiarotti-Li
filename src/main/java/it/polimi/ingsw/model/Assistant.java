package it.polimi.ingsw.model;



public class Assistant  {
    private AssistantType type;
    private Mage mage;

    public Assistant(AssistantType assistantType, Mage mage){
        this.type = assistantType;
        this.mage = mage;
    }

    public int getNum(){
        return this.type.getNum();
    }
    public int getSteps(){
        return this.type.getSteps();
    }

    public AssistantType getAssistantType() {
        return type;
    }
}
