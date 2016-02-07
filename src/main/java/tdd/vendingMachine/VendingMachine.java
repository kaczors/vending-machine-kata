package tdd.vendingMachine;

import tdd.vendingMachine.validation.CoinEntryValidator;
import tdd.vendingMachine.validation.Validator;
import tdd.vendingMachine.validation.exception.CantGiveTheChangeException;
import tdd.vendingMachine.validation.exception.UnsupportedCoinException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static tdd.vendingMachine.util.NumberUtils.isEqualOrGreaterThan;

public class VendingMachine {

    private final CoinContainer coinInputTray = new CoinContainer();
    private final CoinContainer stash = new CoinContainer();
    private final CoinContainer cassette = new CoinContainer();
    private final CoinContainer coinOutputTry = new CoinContainer();
    private final Display display = new Display();
    private final Validator<Coin> coinValidator = new CoinEntryValidator();
    private final ProductStorage productStorage = new ProductStorage();
    private final Collection<Product> outputProductsTry = newArrayList();

    private Optional<Shelf> selectedShelf = Optional.empty();

    public Collection<Coin> getOutputTrayCoins() {
        return coinOutputTry.getAsList();
    }

    public void insertCoin(Coin coin) {
        checkNotNull(coin);

        performActionAndHandleException(() -> {
            coinInputTray.add(coin);
            coinValidator.validate(coin);
            coinInputTray.transferAllCoinsTo(stash);
            tryToBuyProduct();
        });
    }

    public String getMessageFromDisplay() {
        return display.getMessage();
    }

    public void addProduct(int shelfNumber, Product product) {
        checkArgument(shelfNumber > 0);
        checkNotNull(product);

        productStorage.addProduct(shelfNumber, product);
    }

    public void selectShelf(int shelfNumber) {
        checkArgument(shelfNumber > 0);

        performActionAndHandleException(() -> {
            Shelf shelf = productStorage.getShelf(shelfNumber);
            if (shelf.isNotEmpty()) {
                selectedShelf = Optional.of(shelf);
                tryToBuyProduct();
            }
        });
    }

    public Collection<Product> getOutputProductsTray() {
        return outputProductsTry;
    }

    public void cancel() {
        resetToDefaultStateWithMessage(Display::clear);
    }

    public void addCoinsToCassette(Collection<Coin> coins) {
        checkNotNull(coins);

        coins.stream().forEach(cassette::add);
    }

    private void performActionAndHandleException(Action action) {
        try {
            action.perform();
        } catch (UnsupportedCoinException e) {
            coinInputTray.transferAllCoinsTo(coinOutputTry);
        } catch (CantGiveTheChangeException e) {
            resetToDefaultStateWithMessage(d -> d.setMessage(e.getMessage()));
        }
    }

    private void tryToBuyProduct() {
        if (selectedShelf.isPresent()) {
            Shelf shelf = selectedShelf.get();
            if (isEqualOrGreaterThan(stash.getTotalAmount(), shelf.getProductPrice())) {
                buyProductFromShelf(shelf);
                resetToDefaultStateWithMessage(Display::clear);
            } else {
                display.setMessage(getRequiredToCoverProductPriceAmount());
            }
        } else {
            display.setMessage(stash.getTotalAmount());
        }
    }

    private void buyProductFromShelf(Shelf shelf) {
        BigDecimal changeAmount = stash.getTotalAmount().subtract(shelf.getProductPrice());
        if (!changeAmount.equals(BigDecimal.ZERO)) {
            cassette.transferCoinsByAmountTo(changeAmount, coinOutputTry);
            stash.transferAllCoinsTo(cassette);
            shelf.dropProductTo(outputProductsTry);
        }
    }

    private BigDecimal getRequiredToCoverProductPriceAmount() {
        return selectedShelf
            .map(shelf -> shelf.getProductPrice().subtract(stash.getTotalAmount()))
            .orElseThrow(() -> new RuntimeException("No shelf is selected"));
    }

    private void resetToDefaultStateWithMessage(Consumer<Display> displayConsumer) {
        stash.transferAllCoinsTo(coinOutputTry);
        selectedShelf = Optional.empty();
        displayConsumer.accept(display);
    }

    @FunctionalInterface
    interface Action {
        void perform();
    }
}
