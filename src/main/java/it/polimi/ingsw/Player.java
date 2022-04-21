package it.polimi.ingsw;

public class Player {
    private String Name;
    private Mage mage;

    private int A; // int can go from 1-4
    private enum TowerColor{
        WHITE,BLACK,GREY
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    public void assignMage(Mage mage){
       this.mage = mage;
    }
    public Player(){

    }
}
