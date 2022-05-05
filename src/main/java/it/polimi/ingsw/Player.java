package it.polimi.ingsw;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Player implements Serializable {
    private String name;
    private Mage mage;
    private PlayerBoard pb;
    private Hand hand;
    private Wallet wallet;
    private ReentrantLock update = new ReentrantLock();

    public Player(String name, Mage mage, TowerColor towerColor) {
        this.name = name;
        this.mage = mage;
        //this.pb = pb;
        this.towerColor = towerColor;
        hand = new Hand(mage);
        update.lock();
    }

    /**
     * Constructor for expertMode
     * */
    public Player(String name, Mage mage, TowerColor towerColor, Wallet wallet) {
        this.name = name;
        this.mage = mage;
        //this.pb = pb;
        this.towerColor = towerColor;
        hand = new Hand(mage);
        this.wallet = wallet;
    }

    public void unLockUpdate() {
        this.update.unlock();
    }

    public void lockUpdate()
    {
        this.update.lock();
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

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setTowerColor(TowerColor towerColor) {
        this.towerColor = towerColor;
    }
}
