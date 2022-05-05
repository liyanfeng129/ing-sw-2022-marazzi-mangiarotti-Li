package it.polimi.ingsw;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {

    private ArrayList<Assistant> List_cards;
    private int N_cards = 0;

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

    public void setList_cards(Mage mage) {
        for (int i=0 ;i<10;i++){
            this.List_cards.add(new Assistant(AssistantType.index(i),mage));
            this.N_cards = this.N_cards+1;
        }
    }

    //non ci drovebbe essere un exception?
    public void use_cards(Assistant assistant) {
        N_cards = N_cards-1;
        List_cards.remove(List_cards.indexOf(assistant));
    }
}
