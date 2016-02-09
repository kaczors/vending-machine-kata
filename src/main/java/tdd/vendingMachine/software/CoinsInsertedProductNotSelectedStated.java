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
