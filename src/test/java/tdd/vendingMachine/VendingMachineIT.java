package tdd.vendingMachine;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
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
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo("0.00");
    }

    @DataProvider
    public static Object[][] coinsAndExpectedSum() {
        return new Object[][]{
            {newArrayList(Coin._1, Coin._05), "1.50"},
            {newArrayList(Coin._01, Coin._02), "0.30"},
            {newArrayList(Coin._1, Coin._1, Coin._1), "3.00"}
        };
    }

    @Test(dataProvider = "coinsAndExpectedSum")
    public void should_show_sum_of_inserted_coins_before_product_is_selected(List<Coin> coins, String expectedMessage){
        //given
        VendingMachine vendingMachine = new VendingMachine();

        //when
        coins.forEach(vendingMachine::insertCoin);

        //then
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(expectedMessage);
    }
}
