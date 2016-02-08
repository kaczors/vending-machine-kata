package tdd.vendingMachine;

import tdd.vendingMachine.coin.Coin;
import tdd.vendingMachine.hardware.VendingMachineHardware;
import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.software.VendingMachineSoftware;
import tdd.vendingMachine.validation.Validator;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class VendingMachine {

    private final VendingMachineHardware hardware;
    private final VendingMachineSoftware software;

    public VendingMachine(VendingMachineHardware hardware, Validator<Coin> coinValidator) {
        this.hardware = hardware;
        this.software = new VendingMachineSoftware(hardware, coinValidator);
    }

    public void cancel() {
        software.handleCancelClick();
    }

    public void insertCoin(Coin coin) {
        checkNotNull(coin);
        software.handleCoinInsertion(coin);
    }

    public void selectShelf(int shelfNumber) {
        checkArgument(shelfNumber > 0);
        software.handleShelfNumberSelection(shelfNumber);
    }

    public Collection<Coin> getOutputTrayCoins() {
        return hardware.getOutputTrayCoins();
    }

    public String getMessageFromDisplay() {
        return hardware.getMessageFromDisplay();
    }

    public void addProduct(int shelfNumber, Product product) {
        checkArgument(shelfNumber > 0);
        checkNotNull(product);
        hardware.addProduct(shelfNumber, product);
    }

    public Collection<Product> getOutputProductsTray() {
        return hardware.getOutputTrayProducts();
    }

    public void addCoinsToCassette(Collection<Coin> coins) {
        hardware.addCoinsToCassette(coins);
    }

}
