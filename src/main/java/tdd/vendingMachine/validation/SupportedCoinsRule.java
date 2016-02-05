package tdd.vendingMachine.validation;

import tdd.vendingMachine.Coin;

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
    public String getViolationMessage(Coin coin) {
        return "Coin " + coin + " in not supported.";
    }
}
