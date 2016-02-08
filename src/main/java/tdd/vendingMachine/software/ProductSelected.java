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
import tdd.vendingMachine.validation.exception.CantGiveTheChangeException;

import java.math.BigDecimal;

import static tdd.vendingMachine.MessageFormats.AMOUNT_MESSAGE;
import static tdd.vendingMachine.MessageFormats.CANT_GIVE_THE_CHANGE_MESSAGE;
import static tdd.vendingMachine.util.NumberUtils.isEqualOrGreaterThan;

class ProductSelected extends AbstractVendingMachineState {

    ProductSelected(VendingMachineContext context) {
        super(context);
    }

    @Override
    public void onCoinInserted(Coin coin) {
        insertCoinAndProceed(coin, context::goSelf);
    }

    @Override
    public void onStateEntry() {
        Shelf shelf = getSelectedShelf();
        try {
            if (stashedCoinsCoverProductPrice(shelf)) {
                buyProductFromShelf(shelf);
                context.go(StateName.START);
            } else {
                context.getDisplay().setMessage(AMOUNT_MESSAGE, getRequiredToCoverProductPriceAmount());
            }
        } catch (CantGiveTheChangeException e) {
            context.getDisplay().setMessage(CANT_GIVE_THE_CHANGE_MESSAGE);
            context.go(StateName.BUY_OPERATION_FAILED);
        }
    }

    private boolean stashedCoinsCoverProductPrice(Shelf shelf) {
        return isEqualOrGreaterThan(context.getStash().getTotalAmount(), shelf.getProductPrice());
    }

    private BigDecimal getRequiredToCoverProductPriceAmount() {
        return getSelectedShelf().getProductPrice().subtract(context.getStash().getTotalAmount());
    }

    private void buyProductFromShelf(Shelf shelf) {
        BigDecimal changeAmount = context.getStash().getTotalAmount().subtract(shelf.getProductPrice());
        if (!changeAmount.equals(BigDecimal.ZERO)) {
            context.getCassette().transferCoinsByAmountTo(changeAmount, context.getCoinOutputTray());
            context.getStash().transferAllCoinsTo(context.getCassette());
        }
        shelf.dropProductTo(context.getOutputProductsTry());
    }

    private Shelf getSelectedShelf() {
        return context.getSelectedShelf().get();
    }
}
