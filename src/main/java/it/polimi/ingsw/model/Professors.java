package it.polimi.ingsw.model;


import java.util.ArrayList;

public class Professors {
    private Mage[] List_professors= new Mage[5];

    public Professors (){
        for (int i=0;i<5;i++){
            List_professors[i] = Mage.NO_MAGE;
        }
    }
    public void assignProfessor(ArrayList<Player> players){
        int max=0;
        int tie=0;
        int assign=4;
        for(int i=0;i<5;i++){
            for (int j=0;j<players.size();j++){
                if(players.get(j).getPlayerBoard().getDiningRoom()[i] >= max) {
                    if(players.get(j).getPlayerBoard().getDiningRoom()[i] == max)
                        tie=1;
                    else
                        tie=0;
                    max = players.get(j).getPlayerBoard().getDiningRoom()[i];
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
}