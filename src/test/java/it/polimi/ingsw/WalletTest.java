package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    @Test
    public void test_getWallet(){
        Wallet wallet=new Wallet();
        assertEquals(3, wallet.getWallet());
    }
    @Test
    public void test_addCoin(){
        Wallet wallet=new Wallet();
        wallet.addCoin(2);
        assertEquals(5, wallet.getWallet());
    }
    @Test
    public void test_removeCoin(){
        Wallet wallet=new Wallet();
        wallet.removeCoin(2);
        assertEquals(1, wallet.getWallet());
    }

}