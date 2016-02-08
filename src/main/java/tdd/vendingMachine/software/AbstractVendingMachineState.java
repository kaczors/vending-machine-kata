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
import tdd.vendingMachine.hardware.Shelf;
import tdd.vendingMachine.validation.exception.UnsupportedCoinException;

import static com.google.common.base.Preconditions.checkArgument;

abstract class AbstractVendingMachineState implements VendingMachineState {

    final VendingMachineContext context;

    AbstractVendingMachineState(VendingMachineContext context) {
        this.context = context;
    }

    @Override
    public void onCancelClicked() {
        context.go(StateName.START);
    }

    @Override
    public void onShelfNumberSelected(int shelfNumber) {
        checkArgument(shelfNumber > 0);

        Shelf shelf = context.getProductStorage().getShelf(shelfNumber);
        if (shelf.isNotEmpty()) {
            context.selectShelf(shelf);
            context.go(StateName.PRODUCT_SELECTED);
        }
    }

    void insertCoinAndProceed(Coin coin, Action afterCoinInsertion) {
        try {
            context.getCoinInputTray().add(coin);
            context.validateCoin(coin);
            context.getCoinInputTray().transferAllCoinsTo(context.getStash());
            afterCoinInsertion.perform();
        } catch (UnsupportedCoinException e) {
            context.getCoinInputTray().transferAllCoinsTo(context.getCoinOutputTray());
        }
    }

}
