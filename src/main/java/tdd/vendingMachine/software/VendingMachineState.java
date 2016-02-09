package tdd.vendingMachine.software;

import tdd.vendingMachine.coin.Coin;

interface VendingMachineState {
    void onCoinInserted(Coin coin);

    void onCancelClicked();

    void onShelfNumberSelected(int shelfNumber);

    void onStateEntry();
}
