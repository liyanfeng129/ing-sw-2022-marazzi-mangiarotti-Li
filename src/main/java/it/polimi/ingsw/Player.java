package it.polimi.ingsw;

public class Player {
    private String Name;
    private Mage mage;

    private int A; // int can go from 1-4
    private TowerColor towerColor;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    public void assignMage(Mage mage){
       this.mage = mage;
    }

    public Mage getMage() {
        return mage;
    }
    public void assignTower(TowerColor color){
        this.towerColor=color;
    }
    public TowerColor getTowerColor(){
        return towerColor;
    }
    public Player(){
    }
}
