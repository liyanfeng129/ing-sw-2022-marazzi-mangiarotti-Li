package it.polimi.ingsw.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Professors implements Serializable {
    private Mage[] List_professors= new Mage[5];
    private String card2=null;
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
     * this method is also used to compute effects of CharacterCard2
     * Mage type are setted in List_Professors
     * @param players player of the game
     */
    public void assignProfessor(ArrayList<Player> players){
        int max=0;
        int tie=0;
        int assign=4;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < players.size(); j++) {
                    if (players.get(j).getPb().getDiningRoom()[i] >= max) {
                        //check if there is a tie
                        if (players.get(j).getPb().getDiningRoom()[i] == max)
                            tie = 1;
                        else
                            tie = 0;
                        max = players.get(j).getPb().getDiningRoom()[i];
                        assign = j;
                    }
                    if (tie != 1)
                        List_professors[i] = Mage.values()[assign];
                    else
                        List_professors[i] = Mage.values()[4];
                }
                max = 0;
                tie = 0;
                assign = 4;
            }
            if(getCard2()!=null) { // card2 i used
                Player user=players.stream().filter(p -> p.getName().equals(getCard2())).collect(Collectors.toList()).get(0);
                for (int i=0;i<5;i++){
                    for (int j=0;j< players.size();j++){
                        if(user.getPb().getDiningRoom()[i]<players.get(j).getPb().getDiningRoom()[i])
                            tie=1;
                    }
                    if(tie!=1)
                        List_professors[i] = user.getMage();
                    tie=0;
                }
        }
    }

    public Mage[] getList_professors() {
        return List_professors;
    }

    public void setList_professors(Mage[] list_professors) {
        List_professors = list_professors;
    }

    public String getCard2() {
        return card2;
    }

    public void setCard2(String card2) {
        this.card2 = card2;
    }
}