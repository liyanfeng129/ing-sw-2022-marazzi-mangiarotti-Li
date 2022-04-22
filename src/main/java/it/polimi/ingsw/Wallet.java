package it.polimi.ingsw;

public class Wallet {
    private int Saving;

    public int getWallet() {
        return Saving;
    }

    public void addCoin(int n){
        Saving=Saving+n;
    }

    public void removeCoin(int n) throws EriantysExceptions{
        if((Saving-n)<0)
            throw new InnerExceptions.NotEnoughMoneyException("Not enough money on wallet");
        Saving=Saving-n;
    }
    public Wallet(){
        this.Saving=3;
    }
}
