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
