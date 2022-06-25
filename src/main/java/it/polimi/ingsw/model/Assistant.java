package it.polimi.ingsw.model;


import java.io.Serializable;

public class Assistant implements Serializable {
    private AssistantType type;
    private Mage mage;

    /**
     *Constructors of Assistant
     * @param assistantType enum of the cards
     * @param mage mage who this assistant belong to
     */
    public Assistant(AssistantType assistantType, Mage mage){
        this.type = assistantType;
        this.mage = mage;
    }

    /**
     * get the value of the card (from 1 to 10)
     * @return the value of the card
     */
    public int getNum(){
        return this.type.getNum();
    }
    /**
     * get the number of steps allowed by this card (from 1 to 5)
     * @return number of steps
     */
    public int getSteps(){
        return this.type.getSteps();
    }


    /**
     * get the type of this asssistant
     * @return the enum of this card
     */
    public AssistantType getType() {
        return type;
    }

    /* non li usiamo mai
    public void setType(AssistantType type) {
        this.type = type;
    }

    public void setMage(Mage mage) {
        this.mage = mage;
    }
*/
    /**
     * get the mage of this assistant
     * @return the mage who this card belong to
     */
    public Mage getMage() {
        return mage;
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
