package it.polimi.ingsw;

public class Player {
    private String Name;
    private enum Mage{
        MAGE1,MAGE2,MAGE3,MAGE4
    }
    private enum TowerColor{
        WHITE,BLACK,GREY
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
