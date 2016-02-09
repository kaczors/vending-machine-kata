package tdd.vendingMachine.hardware;

import tdd.vendingMachine.product.Product;

import java.util.Collection;

public interface VendingMachinePartsSupplier {

    CoinContainer getCoinInputTray();

    CoinContainer getStash();

    CoinContainer getCassette();

    CoinContainer getCoinOutputTray();

    Display getDisplay();

    ProductStorage getProductStorage();

    Collection<Product> getOutputProductsTry();
}
