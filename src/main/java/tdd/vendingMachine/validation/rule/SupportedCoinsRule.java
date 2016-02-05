package tdd.vendingMachine.validation.rule;

import tdd.vendingMachine.Coin;
import tdd.vendingMachine.validation.exception.BusinessRuleValidationException;
import tdd.vendingMachine.validation.exception.UnsupportedCoinException;

import java.util.Set;

public class SupportedCoinsRule implements Rule<Coin> {

    private final Set<Coin> supportedCoins;

    public SupportedCoinsRule(Set<Coin> supportedCoins) {
        this.supportedCoins = supportedCoins;
    }

    @Override
    public boolean isValid(Coin coin) {
        return supportedCoins.contains(coin);
    }

    @Override
    public BusinessRuleValidationException getException(Coin coin) {
        return new UnsupportedCoinException(coin);
    }

}
