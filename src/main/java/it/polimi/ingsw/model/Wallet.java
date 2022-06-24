package it.polimi.ingsw.model;

import java.io.Serializable;

public class Wallet implements Serializable {
    private int Saving;

    public int getSaving() {
        return Saving;
    }

    public void addCoin(int n){
        Saving=Saving+n;
    }

    public void removeCoin(int n) throws EriantysExceptions{
        if((Saving-n)<0)
            throw new InnerExceptions.NegativeValue("Not enough money on wallet");
        Saving=Saving-n;
    }
    public Wallet(){
        this.Saving=1;
    }
}
