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
package tdd.vendingMachine.software;

import tdd.vendingMachine.coin.Coin;
import tdd.vendingMachine.hardware.CoinContainer;
import tdd.vendingMachine.hardware.Display;
import tdd.vendingMachine.hardware.ProductStorage;
import tdd.vendingMachine.hardware.Shelf;
import tdd.vendingMachine.hardware.VendingMachineHardware;
import tdd.vendingMachine.hardware.VendingMachinePartsSupplier;
import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.validation.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import static com.google.common.collect.Maps.newHashMap;

class VendingMachineContext implements VendingMachinePartsSupplier {

    private HashMap<StateName, VendingMachineState> states = newHashMap();
    private VendingMachineHardware vendingMachineHardware;
    private Validator<Coin> coinValidator;
    private Optional<Shelf> selectedShelf = Optional.empty();
    private VendingMachineState currentState;

    public void addState(StateName stateName, VendingMachineState state) {
        states.put(stateName, state);
    }

    @Override
    public CoinContainer getCoinInputTray() {
        return vendingMachineHardware.getCoinInputTray();
    }

    @Override
    public CoinContainer getStash() {
        return vendingMachineHardware.getStash();
    }

    @Override
    public CoinContainer getCassette() {
        return vendingMachineHardware.getCassette();
    }

    @Override
    public CoinContainer getCoinOutputTray() {
        return vendingMachineHardware.getCoinOutputTray();
    }

    @Override
    public Display getDisplay() {
        return vendingMachineHardware.getDisplay();
    }

    @Override
    public ProductStorage getProductStorage() {
        return vendingMachineHardware.getProductStorage();
    }

    @Override
    public Collection<Product> getOutputProductsTry() {
        return vendingMachineHardware.getOutputProductsTry();
    }

    public void go(StateName stateName) {
        currentState = states.get(stateName);
        currentState.onStateEntry();
    }

    public void goSelf() {
        currentState.onStateEntry();
    }

    public VendingMachineState getCurrentState() {
        return currentState;
    }

    public void selectShelf(Shelf shelf) {
        this.selectedShelf = Optional.of(shelf);
    }

    public void clearShelfSelection() {
        this.selectedShelf = Optional.empty();
    }

    public Optional<Shelf> getSelectedShelf() {
        return selectedShelf;
    }

    public void validateCoin(Coin coin) {
        coinValidator.validate(coin);
    }

    public static class Builder {
        private final VendingMachineContext context = new VendingMachineContext();
        private VendingMachineHardware vendingMachineHardware;
        private Validator<Coin> coinValidator;

        private Builder() {
        }

        public static Builder vendingMachineContext() {
            return new Builder();
        }

        public Builder withState(StateName stateName, Function<VendingMachineContext, VendingMachineState> stateProvider) {
            context.addState(stateName, stateProvider.apply(context));
            return this;
        }

        public Builder withVendingMachineHardware(VendingMachineHardware vendingMachineHardware) {
            this.vendingMachineHardware = vendingMachineHardware;
            return this;
        }

        public Builder withCoinValidator(Validator<Coin> validator) {
            this.coinValidator = validator;
            return this;
        }

        public VendingMachineContext startFrom(StateName stateName) {
            context.vendingMachineHardware = vendingMachineHardware;
            context.coinValidator = coinValidator;
            context.go(stateName);
            return context;
        }

    }
}
