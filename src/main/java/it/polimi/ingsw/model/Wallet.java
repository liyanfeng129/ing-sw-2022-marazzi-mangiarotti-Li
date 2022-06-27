package it.polimi.ingsw.model;

import java.io.Serializable;

public class Wallet implements Serializable {
    private int Saving;

    public int getSaving() {
        return Saving;
    }

    /**
     * add n saving to the wallet
     * @param n number of saving
     */
    public void addCoin(int n){
        Saving=Saving+n;
    }

    /**
     * remove n saving from wallet
     * @param n number of saving
     * @throws EriantysExceptions if there is not enough saving in wallet
     */
    public void removeCoin(int n) throws EriantysExceptions{
        if((Saving-n)<0)
            throw new InnerExceptions.NegativeValue("Not enough money on wallet");
        Saving=Saving-n;
    }

    /**
     * constructor of class Wallet
     * initialize saving at 1
     */
    public Wallet(){
        this.Saving=1;
    }
}
