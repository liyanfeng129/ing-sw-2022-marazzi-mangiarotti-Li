package it.polimi.ingsw.model;


import java.io.Serializable;

public class Assistant implements Serializable {
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



    public AssistantType getType() {
        return type;
    }

    public void setType(AssistantType type) {
        this.type = type;
    }

    public Mage getMage() {
        return mage;
    }

    public void setMage(Mage mage) {
        this.mage = mage;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
