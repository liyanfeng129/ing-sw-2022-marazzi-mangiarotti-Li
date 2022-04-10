package it.polimi.ingsw;

public class Wallet {
    private int Saving;

    public int getWallet() {
        return Saving;
    }
    //probabilmente va eliminato
    public void setWallet(int saving) {
        Saving = saving;
    }
    public void addCoin(int n){
        Saving=Saving+n;
    }
    public void removeCoin(int n){
        Saving=Saving-n;
    }
}
