package tdd.vendingMachine.validation.exception;

import tdd.vendingMachine.coin.Coin;

public class UnsupportedCoinException extends ApplicationException {
    public UnsupportedCoinException(Coin coin) {
        super("Coin " + coin + " in not supported.");
    }
}
