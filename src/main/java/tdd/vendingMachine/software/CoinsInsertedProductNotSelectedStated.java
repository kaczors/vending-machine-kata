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

import static tdd.vendingMachine.MessageFormats.AMOUNT_MESSAGE;

class CoinsInsertedProductNotSelectedStated extends AbstractVendingMachineState {

    CoinsInsertedProductNotSelectedStated(VendingMachineContext context) {
        super(context);
    }

    @Override
    public void onCoinInserted(Coin coin) {
        insertCoinAndProceed(coin, context::goSelf);
    }

    @Override
    public void onStateEntry() {
        context.getDisplay().setMessage(AMOUNT_MESSAGE, context.getStash().getTotalAmount());
    }
}
