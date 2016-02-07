package tdd.vendingMachine;

import tdd.vendingMachine.validation.CoinEntryValidator;
import tdd.vendingMachine.validation.Validator;
import tdd.vendingMachine.validation.exception.CantGiveTheChangeException;
import tdd.vendingMachine.validation.exception.UnsupportedCoinException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;
import static tdd.vendingMachine.util.NumberUtils.isEqualOrGreaterThan;

public class VendingMachine {

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
        try {
            coinValidator.validate(coin);
            stash.add(coin);
            tryToBuyProduct();
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
            tryToBuyProduct();
        }
    }

    public Collection<Product> getOutputProductsTray() {
        return outputProductsTry;
    }

    private void tryToBuyProduct() {
        try {
            if (selectedShelf.isPresent()) {
                Shelf shelf = selectedShelf.get();
                if (isEqualOrGreaterThan(stash.getTotalAmount(), shelf.getProductPrice())) {
                    BigDecimal changeAmount = stash.getTotalAmount().subtract(shelf.getProductPrice());
                    if (!changeAmount.equals(BigDecimal.ZERO)) {
                        cassette.transferCoinsByAmountTo(changeAmount, coinOutputTry);
                        stash.transferAllCoinsTo(cassette);
                        shelf.dropProductTo(outputProductsTry);
                    }
                    setToDefaultStateWithMessage(Display::clear);
                }else{
                    display.setMessage(getRequiredToCoverProductPriceAmount());
                }
            } else {
                display.setMessage(stash.getTotalAmount());
            }
        } catch (CantGiveTheChangeException e) {
            display.setMessage(e.getMessage());
            setToDefaultStateWithMessage(d -> d.setMessage(e.getMessage()));
        }
    }

    private BigDecimal getRequiredToCoverProductPriceAmount(){
        return selectedShelf
                .map(shelf -> shelf.getProductPrice().subtract(stash.getTotalAmount()))
                .orElseThrow(() -> new RuntimeException("No shelf is selected"));
    }

    public void cancel() {
        setToDefaultStateWithMessage(Display::clear);
    }

    public void addCoinsToCassette(Collection<Coin> coins) {
        coins.stream().forEach(cassette::add);
    }

    private void setToDefaultStateWithMessage(Consumer<Display> displayConsumer){
        stash.transferAllCoinsTo(coinOutputTry);
        selectedShelf = Optional.empty();
        displayConsumer.accept(display);
    }
}
