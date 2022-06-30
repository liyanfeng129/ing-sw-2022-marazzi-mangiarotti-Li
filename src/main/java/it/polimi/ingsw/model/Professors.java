package it.polimi.ingsw.model;


import java.io.Serializable;
import java.util.ArrayList;

public class Professors implements Serializable {
    private Mage[] List_professors= new Mage[5];
    private boolean card2=false;
    /**
     * constructor for class Professor
     * initialize List_Professors to NO_MAGE
     */
    public Professors (){
        for (int i=0;i<5;i++){
            List_professors[i] = Mage.NO_MAGE;
        }
    }

    /**
     * Compute which Mage each professor belong to
     * Mage type are setted in List_Professors
     * @param players player of the game
     */
    public void assignProfessor(ArrayList<Player> players){
        int max=0;
        int tie=0;
        int assign=4;
        for(int i=0;i<5;i++){
            for (int j=0;j<players.size();j++){
                if(players.get(j).getPb().getDiningRoom()[i] >= max) {
                    if(players.get(j).getPb().getDiningRoom()[i] == max && !isCard2())
                        tie=1;
                    else
                        tie=0;
                    max = players.get(j).getPb().getDiningRoom()[i];
                    assign=j;
                }
                if(tie!=1)
                    List_professors[i] = Mage.values()[assign];
                else
                    List_professors[i] = Mage.values()[4];
            }
            max=0;
            tie=0;
            assign=4;
        }
    }

    public Mage[] getList_professors() {
        return List_professors;
    }

    public void setList_professors(Mage[] list_professors) {
        List_professors = list_professors;
    }

    public boolean isCard2() {
        return card2;
    }

    public void setCard2(boolean card2) {
        this.card2 = card2;
    }
}