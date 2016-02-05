package tdd.vendingMachine.validation;

import tdd.vendingMachine.Coin;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class CoinEntryValidator extends AbstractValidator<Coin> {

    public CoinEntryValidator() {
        super(newArrayList(
            new SupportedCoinsRule(
                newHashSet(Coin._01, Coin._02, Coin._05, Coin._1, Coin._2, Coin._5))));
    }

}
