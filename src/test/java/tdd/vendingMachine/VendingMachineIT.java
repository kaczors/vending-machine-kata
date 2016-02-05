package tdd.vendingMachine;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VendingMachineIT {

    @DataProvider
    public static Object[][] unsupportedCoins() {
        return new Object[][]{
            {Coin._10}, {Coin._20}
        };
    }

    @Test(dataProvider = "unsupportedCoins")
    public void should_reject_unsupported_coin(Coin unsupportedCoin) {
        //given
        VendingMachine vendingMachine = new VendingMachine();

        //when
        vendingMachine.insertCoin(unsupportedCoin);

        //then
        assertThat(vendingMachine.getOutputTrayCoins()).containsExactly(unsupportedCoin);
    }
}
