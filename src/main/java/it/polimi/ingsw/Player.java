package it.polimi.ingsw;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private Mage mage;
    private PlayerBoard pb;
    private Hand hand;
    private Wallet wallet;

    public Player(String name, Mage mage, PlayerBoard pb, TowerColor towerColor) {
        this.name = name;
        this.mage = mage;
        this.pb = pb;
        this.towerColor = towerColor;
        hand = new Hand(mage);
    }

    /**
     * Constructor for expertMode
     * */
    public Player(String name, Mage mage, PlayerBoard pb, TowerColor towerColor, Wallet wallet) {
        this.name = name;
        this.mage = mage;
        this.pb = pb;
        this.towerColor = towerColor;
        hand = new Hand(mage);
        this.wallet = wallet;
    }


    private TowerColor towerColor;

    /** TODO
     * player board va aggiunta come attributo
     */






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
