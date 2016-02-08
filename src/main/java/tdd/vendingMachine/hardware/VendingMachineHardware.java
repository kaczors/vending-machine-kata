/*
 * This document set is the property of GTECH Corporation, Providence,
 * Rhode Island, and contains confidential and trade secret information.
 * It cannot be transferred from the custody or control of GTECH except as
 * authorized in writing by an officer of GTECH. Neither this item nor
 * the information it contains can be used, transferred, reproduced, published,
 * or disclosed, in whole or in part, directly or indirectly, except as
 * expressly authorized by an officer of GTECH, pursuant to written agreement.
 *
 * Copyright 2015 GTECH Corporation. All Rights Reserved.
 */
package tdd.vendingMachine.hardware;

import tdd.vendingMachine.coin.Coin;
import tdd.vendingMachine.product.Product;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class VendingMachineHardware implements VendingMachinePartsSupplier {

    private final CoinContainer coinInputTray;
    private final CoinContainer stash;
    private final CoinContainer cassette;
    private final CoinContainer coinOutputTray;
    private final Display display;
    private final ProductStorage productStorage;
    private final Collection<Product> outputProductsTry;

    public VendingMachineHardware(CoinContainer coinInputTray, CoinContainer stash, CoinContainer cassette, CoinContainer coinOutputTray, Display display, ProductStorage productStorage, Collection<Product> outputProductsTry) {
        this.coinInputTray = coinInputTray;
        this.stash = stash;
        this.cassette = cassette;
        this.coinOutputTray = coinOutputTray;
        this.display = display;
        this.productStorage = productStorage;
        this.outputProductsTry = outputProductsTry;
    }

    public void addProduct(int shelfNumber, Product product) {
        productStorage.addProduct(shelfNumber, product);
    }

    public Collection<Product> getOutputTrayProducts() {
        return outputProductsTry;
    }

    public void addCoinsToCassette(Collection<Coin> coins) {
        checkNotNull(coins);
        coins.stream().forEach(cassette::add);
    }

    public String getMessageFromDisplay() {
        return display.getMessage();
    }

    public Collection<Coin> getOutputTrayCoins() {
        return coinOutputTray.getCoinList();
    }

    @Override
    public Display getDisplay() {
        return display;
    }

    @Override
    public ProductStorage getProductStorage() {
        return productStorage;
    }

    @Override
    public Collection<Product> getOutputProductsTry() {
        return outputProductsTry;
    }

    @Override
    public CoinContainer getCoinInputTray() {
        return coinInputTray;
    }

    @Override
    public CoinContainer getStash() {
        return stash;
    }

    @Override
    public CoinContainer getCassette() {
        return cassette;
    }

    @Override
    public CoinContainer getCoinOutputTray() {
        return coinOutputTray;
    }

}
