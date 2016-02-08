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

class BuyOperationFailed extends AbstractVendingMachineState {

    BuyOperationFailed(VendingMachineContext context) {
        super(context);
    }

    @Override
    public void onCoinInserted(Coin coin) {
        insertCoinAndProceed(coin, () -> context.go(StateName.COINS_INSERTED_PRODUCT_NOT_SELECTED));
    }

    @Override
    public void onStateEntry() {
        context.getStash().transferAllCoinsTo(context.getCoinOutputTray());
        context.clearShelfSelection();
    }
}
