package it.polimi.ingsw;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Assistant> List_cards;
    private int N_cards = 0;

    public Hand(){
        this.List_cards= new ArrayList<Assistant>();
    }




    public int getN_cards() {
        return N_cards;
    }

    public ArrayList<Assistant> getList_cards(){
        return this.List_cards;
    }

    public void setList_cards(Mage mage) {
        for (int i=0 ;i<10;i++){
            this.List_cards.add(new Assistant(AssistantType.index(i),mage));
            this.N_cards = this.N_cards+1;
        }
    }

    //void perche so gia la carta che il player usa,altimenti bisogna cambiare un po di cose
    //non ci drovebbe essere un exception?
    public void use_cards(Assistant assistant) {
        N_cards = N_cards-1;
        List_cards.remove(List_cards.indexOf(assistant));
    }
}
