package it.polimi.ingsw.model;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private Mage mage;
    private PlayerBoard pb;
    private Hand hand;
    private Wallet wallet;
    private TowerColor towerColor;
    private boolean update;
    private boolean cliClient;


    /**
     * constructo of class Player
     * initialize wallet of the player
     * initialize the correct playerBoard of the player based on the number of player in game
     * @param name initialize name of the player
     * @param mage initialize mage of the player
     * @param towerColor initialize towerColor of the player
     * @param n_Player initialize number of player
     * @param cliClient initialize if is a match on cli or on GUI
     */
    public Player(String name, Mage mage, TowerColor towerColor,int n_Player, boolean cliClient) {
        this.name = name;
        this.mage = mage;
        this.cliClient = cliClient;
        /**TODO YAN
         * perché prima andava?
         */
        this.wallet = new Wallet();
        if (n_Player ==2)
            this.pb = new PlayerBoard(8,towerColor,7);
        if (n_Player ==3)
            this.pb = new PlayerBoard(6,towerColor,9);
        this.towerColor = towerColor;
        hand = new Hand(mage);
        update = false;
    }

    /**
     * TODO
     * non ho idea di cosa serva
     * @param name
     */
    public Player(String name)
    {
        this.name = name;
    }

    public boolean isCliClient() {
        return cliClient;
    }

    public void setCliClient(boolean cliClient) {
        this.cliClient = cliClient;
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
    public void assignTowerColor(TowerColor color){
        this.towerColor=color;
    }
    public TowerColor getTowerColor(){
        return towerColor;
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
