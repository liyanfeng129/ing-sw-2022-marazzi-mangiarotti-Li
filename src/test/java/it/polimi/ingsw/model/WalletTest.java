package it.polimi.ingsw.model;

import it.polimi.ingsw.model.EriantysExceptions;
import it.polimi.ingsw.model.InnerExceptions;
import it.polimi.ingsw.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private Wallet wallet;
    @BeforeEach
    public void setup(){
        wallet=new Wallet();
    }
    @Test
    public void test_getWallet(){
        assertEquals(1, wallet.getSaving());
    }
    @Test
    public void test_addCoin(){
        wallet.addCoin(2);
        assertEquals(3, wallet.getSaving());
    }
    @Test
    public void test_removeCoin() throws EriantysExceptions {
        try {
            wallet.removeCoin(1);
        } catch (InnerExceptions.NegativeValue e) {
            e.printStackTrace();
        }
        assertEquals(0, wallet.getSaving());
    }
    @Test
    public void test_removeCoin_NegativeValue_exception(){
        try {
            wallet.removeCoin(5);
        } catch (EriantysExceptions e) {
            e.printStackTrace();
        }
    }

}