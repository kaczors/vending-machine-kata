package tdd.vendingMachine;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class CoinContainerTest {

    @Test
    public void should_be_empty_after_creation() {
        assertThat(new CoinContainer().getTotalAmount()).isZero();
    }

    @DataProvider
    public static Object[][] coinsWithExpectedAmout() {
        return new Object[][]{
            {newArrayList(Coin._01), BigDecimal.valueOf(0.1)},
            {newArrayList(Coin._01, Coin._02), BigDecimal.valueOf(0.3)},
            {newArrayList(Coin._02, Coin._5), BigDecimal.valueOf(5.2)},
            {newArrayList(Coin._05, Coin._05, Coin._02, Coin._1), BigDecimal.valueOf(2.2)}
        };

    }

    @Test(dataProvider = "coinsWithExpectedAmout")
    public void should_accept_coin_and_calculate_total_amount(Collection<Coin> coins, BigDecimal expectedAmount) {
        //given
        CoinContainer coinContainer = new CoinContainer();

        //when
        coins.forEach(coinContainer::add);

        //then
        assertThat(coinContainer.getTotalAmount()).isEqualTo(expectedAmount);
    }

    @DataProvider
    public static Object[][] coinLists() {
        return new Object[][]{
            {newArrayList(Coin._02)},
            {newArrayList(Coin._02, Coin._02)},
            {newArrayList(Coin._05, Coin._5)},
            {newArrayList(Coin._01, Coin._02, Coin._02, Coin._1)}
        };
    }

    @Test(dataProvider = "coinLists")
    public void should_return_coins_as_list(Collection<Coin> coins){
        //given
        CoinContainer coinContainer = new CoinContainer();

        //when
        coins.forEach(coinContainer::add);

        //then
        assertThat(coinContainer.getAsList()).containsOnlyElementsOf(coins);
    }

    @Test(dataProvider = "coinLists")
    public void should_transfer_coins_to_other_container(Collection<Coin> coins){
        //given
        CoinContainer fromContainer = new CoinContainer();
        CoinContainer toContainer = new CoinContainer();
        coins.forEach(fromContainer::add);

        //when
        fromContainer.transferCoinsTo(toContainer);

        //then
        assertThat(toContainer.getAsList()).containsOnlyElementsOf(coins);
        assertThat(fromContainer.getAsList()).isEmpty();
    }
}
