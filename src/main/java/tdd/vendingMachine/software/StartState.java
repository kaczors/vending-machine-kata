package tdd.vendingMachine.software;

import tdd.vendingMachine.coin.Coin;

class StartState extends AbstractVendingMachineState {

    StartState(VendingMachineContext context) {
        super(context);
    }

    @Override
    public void onCoinInserted(Coin coin) {
        insertCoinAndProceed(coin, () -> {
            context.go(StateName.COINS_INSERTED_PRODUCT_NOT_SELECTED);
        });
    }

    @Override
    public void onStateEntry() {
        context.getStash().transferAllCoinsTo(context.getCoinOutputTray());
        context.clearShelfSelection();
        context.getDisplay().clear();
    }

}
