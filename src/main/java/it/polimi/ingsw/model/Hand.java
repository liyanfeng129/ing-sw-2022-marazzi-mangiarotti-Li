package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {

    private ArrayList<Assistant> List_cards;
    private int N_cards = 0;
    private int LastStepsAssistant;
    private int LastValueAssistant;

    /**
     * constructor for class Hand
     * initialize the mage who these assistants belong to
     * @param mage owner of these assistants
     */
    public Hand(Mage mage){
        this.List_cards= new ArrayList<Assistant>();
        setList_cards(mage);
    }




    public int getN_cards() {
        return N_cards;
    }

    public ArrayList<Assistant> getList_cards(){
        return this.List_cards;
    }

    /**
     * set the assistants from 1 to 10
     * @param mage owner of these assistants
     */
    public void setList_cards(Mage mage) {
        for (int i=0 ;i<10;i++){
            this.List_cards.add(new Assistant(AssistantType.index(i),mage));
            this.N_cards = this.N_cards+1;
        }
    }

    /**
     * remove the assistant card used in game from List_cards
     * @param assistant the assistant to be removed
     */
    public synchronized void use_cards(AssistantType assistant) {
        for (int i=0;i<N_cards;i++)
            if(List_cards.get(i).getType() == assistant)
            {
                List_cards.remove(i);
                i = N_cards+1; // break the for circle
            }
        N_cards = List_cards.size();
    }


    public int getLastStepsAssistant() {
        return LastStepsAssistant;
    }

    public void setLastStepsAssistant(int lastStepsAssistant) {
        this.LastStepsAssistant = lastStepsAssistant;
    }

    public int getLastValueAssistant() {
        return LastValueAssistant;
    }

    public void setLastValueAssistant(int lastValueAssistant) {
        this.LastValueAssistant = lastValueAssistant;
    }
}
