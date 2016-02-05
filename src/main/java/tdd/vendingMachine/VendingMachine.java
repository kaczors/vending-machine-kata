package tdd.vendingMachine;

import tdd.vendingMachine.validation.CoinEntryValidator;
import tdd.vendingMachine.validation.Validator;
import tdd.vendingMachine.validation.exception.UnsupportedCoinException;

import java.util.Collection;

public class VendingMachine {

    private final CoinContainer stash = new CoinContainer();
    private final CoinContainer cassette = new CoinContainer();
    private final CoinContainer coinOutputTry = new CoinContainer();
    private final Display display = new Display();
    private final Validator<Coin> coinValidator = new CoinEntryValidator();

    public Collection<Coin> getOutputTrayCoins() {
        return coinOutputTry.getAsList();
    }

    public void insertCoin(Coin coin) {
        try {
            coinValidator.validate(coin);
            stash.add(coin);
            display.setMessage(stash.getTotalAmount());
        } catch (UnsupportedCoinException e) {
            coinOutputTry.add(coin);
        }
    }

    public String getMessageFromDisplay() {
        return display.getMessage();
    }
}
