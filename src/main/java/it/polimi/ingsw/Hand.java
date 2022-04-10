package it.polimi.ingsw;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Assistant> List_cards = new ArrayList<Assistant>();
    private int N_cards = List_cards.size();






    public int getN_cards() {
        return N_cards;
    }

    public ArrayList<Assistant> getList_cards(){
        return this.List_cards;
    }

    public void setList_cards(Mage mage) {
        for (int i=0 ;i<11;i++){
            this.List_cards.add(new Assistant(AssistantType.index(i),mage));
        }
    }

    //void perche so gia la carta che il player usa,altimenti bisogna cambiare un po di cose
    public void use_cards(Assistant assistant) {
        N_cards = N_cards-1;
        List_cards.remove(List_cards.indexOf(assistant));
    }
}
