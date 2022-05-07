package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

public class Player implements Serializable {
    private String name;
    private Mage mage;
    private PlayerBoard pb;
    private Hand hand;
    private Wallet wallet;
    private TowerColor towerColor;
    private boolean update;


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
        update = false;

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
        update = false;
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

    public void setMage(Mage mage) {
        this.mage = mage;
    }

    public PlayerBoard getPb() {
        return pb;
    }

    public void setPb(PlayerBoard pb) {
        this.pb = pb;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setTowerColor(TowerColor towerColor) {
        this.towerColor = towerColor;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
