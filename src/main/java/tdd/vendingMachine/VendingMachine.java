package tdd.vendingMachine;

import tdd.vendingMachine.validation.CoinEntryValidator;
import tdd.vendingMachine.validation.Validator;
import tdd.vendingMachine.validation.exception.UnsupportedCoinException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class VendingMachine {

    private final CoinContainer stash = new CoinContainer();
    private final CoinContainer cassette = new CoinContainer();
    private final CoinContainer coinOutputTry = new CoinContainer();
    private final Display display = new Display();
    private final Validator<Coin> coinValidator = new CoinEntryValidator();
    private final ProductStorage productStorage = new ProductStorage();

    private Optional<Shelf> selectedShelf = Optional.empty();

    public Collection<Coin> getOutputTrayCoins() {
        return coinOutputTry.getAsList();
    }

    public void insertCoin(Coin coin) {
        try {
            coinValidator.validate(coin);
            stash.add(coin);
            proceed();
        } catch (UnsupportedCoinException e) {
            coinOutputTry.add(coin);
        }
    }

    public String getMessageFromDisplay() {
        return display.getMessage();
    }

    public void addProduct(int i, Product product) {
        productStorage.addProduct(i, product);
    }

    public void selectShelf(int shelfNumber) {
        Shelf shelf = productStorage.getShelf(shelfNumber);
        if (shelf.isNotEmpty()) {
            selectedShelf = Optional.of(shelf);
            proceed();
        }
    }

    public Collection<Product> getOutputTrayProducts() {
        return emptyList();
    }

    private void proceed() {
        display.setMessage(getAmountToShow());
    }

    private BigDecimal getAmountToShow() {
        return selectedShelf
            .map(s -> s.getProductPrice().subtract(stash.getTotalAmount()))
            .orElse(stash.getTotalAmount());
    }

    public void cancel() {
        stash.transferCoinsTo(coinOutputTry);
        selectedShelf = Optional.empty();
        display.clear();
    }
}
