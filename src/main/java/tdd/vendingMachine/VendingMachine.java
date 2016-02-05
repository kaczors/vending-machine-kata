package tdd.vendingMachine;

import tdd.vendingMachine.validation.BusinessRuleValidationException;
import tdd.vendingMachine.validation.CoinEntryValidator;
import tdd.vendingMachine.validation.Validator;

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
        try{
            coinValidator.validate(coin);
        }catch (BusinessRuleValidationException e){
            coinOutputTry.add(coin);
        }
    }

}
