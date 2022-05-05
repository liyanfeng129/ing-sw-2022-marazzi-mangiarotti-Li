package it.polimi.ingsw.model;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private Mage mage;
    private PlayerBoard pb;
    private Hand hand;
    private Wallet wallet;
    private TowerColor towerColor;

    //non sarebbe meglio fare new pb invece che passargli una pb?
    public Player(String name, Mage mage, TowerColor towerColor,int n_Player, boolean leader) {
        this.name = name;
        this.mage = mage;
        if (n_Player ==2)
            this.pb = new PlayerBoard(8,towerColor,7);
        if (n_Player ==3)
            this.pb = new PlayerBoard(6,towerColor,9);
        if (n_Player ==4 && leader == true)
            this.pb = new PlayerBoard(8,towerColor,7);
        if (n_Player ==4 && leader == false)
            this.pb = new PlayerBoard(0,towerColor,7);
        this.towerColor = towerColor;
        hand = new Hand(mage);
    }

    /**
     * Constructor for expertMode
     * */
    public Player(String name, Mage mage, TowerColor towerColor, Wallet wallet,int n_Player, boolean leader) {
        this.name = name;
        this.mage = mage;
        if (n_Player ==2)
            this.pb = new PlayerBoard(8,towerColor,7);
        if (n_Player ==3)
            this.pb = new PlayerBoard(6,towerColor,9);
        if (n_Player ==4 && leader == true)
            this.pb = new PlayerBoard(8,towerColor,7);
        if (n_Player ==4 && leader == false)
            this.pb = new PlayerBoard(0,towerColor,7);
        this.towerColor = towerColor;
        hand = new Hand(mage);
        this.wallet = wallet;
    }







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

    public PlayerBoard getPlayerBoard() {
        return pb;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
