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
import tdd.vendingMachine.hardware.VendingMachineHardware;
import tdd.vendingMachine.validation.Validator;

import static tdd.vendingMachine.software.VendingMachineContext.Builder.vendingMachineContext;

public class VendingMachineSoftware {

    private final VendingMachineContext context;

    public VendingMachineSoftware(VendingMachineHardware vendingMachineHardware, Validator<Coin> coinValidator) {
        context = vendingMachineContext()
            .withVendingMachineHardware(vendingMachineHardware)
            .withCoinValidator(coinValidator)
            .withState(StateName.START, StartState::new)
            .withState(StateName.COINS_INSERTED_PRODUCT_NOT_SELECTED, CoinsInsertedProductNotSelectedStated::new)
            .withState(StateName.PRODUCT_SELECTED, ProductSelected::new)
            .withState(StateName.BUY_OPERATION_FAILED, BuyOperationFailed::new)
            .startFrom(StateName.START);
    }

    public void handleCoinInsertion(Coin coin) {
        context.getCurrentState().onCoinInserted(coin);
    }

    public void handleCancelClick() {
        context.getCurrentState().onCancelClicked();
    }

    public void handleShelfNumberSelection(int shelfNumber) {
        context.getCurrentState().onShelfNumberSelected(shelfNumber);
    }

}
