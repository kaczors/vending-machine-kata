package tdd.vendingMachine.validation.exception;

import tdd.vendingMachine.Coin;

public class UnsupportedCoinException extends BusinessRuleValidationException {
    public UnsupportedCoinException(Coin coin) {
        super("Coin " + coin + " in not supported.");
    }
}
