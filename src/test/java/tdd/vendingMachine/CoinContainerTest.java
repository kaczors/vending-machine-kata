package tdd.vendingMachine;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tdd.vendingMachine.validation.exception.CantGiveTheChangeException;

import java.math.BigDecimal;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        coins.forEach(fromContainer::add);
        CoinContainer toContainer = new CoinContainer();

        //when
        fromContainer.transferAllCoinsTo(toContainer);

        //then
        assertThat(toContainer.getAsList()).containsOnlyElementsOf(coins);
        assertThat(fromContainer.getAsList()).isEmpty();
    }

    @DataProvider
    public static Object[][] achievableCoinsWithTransferAmount() {
        return new Object[][]{
            {newArrayList(Coin._5, Coin._2, Coin._1), BigDecimal.valueOf(3)},
            {newArrayList(Coin._01, Coin._02, Coin._02), BigDecimal.valueOf(0.3)},
            {newArrayList(Coin._02, Coin._5, Coin._2), BigDecimal.valueOf(2.2)},
            {newArrayList(Coin._1, Coin._05, Coin._05, Coin._01, Coin._01, Coin._01), BigDecimal.valueOf(2.2)}
        };
    }

    @Test(dataProvider = "achievableCoinsWithTransferAmount")
    public void should_transfer_coins_to_other_container_by_given_amount(Collection<Coin> coins,  BigDecimal transferAmount){
        //given
        CoinContainer fromContainer = new CoinContainer();
        coins.forEach(fromContainer::add);
        BigDecimal fromContainerBeforeTransferAmount = fromContainer.getTotalAmount();
        CoinContainer toContainer = new CoinContainer();

        //when
        fromContainer.transferCoinsByAmountTo(transferAmount, toContainer);

        //then
        assertThat(toContainer.getTotalAmount()).isEqualByComparingTo(transferAmount);
        assertThat(fromContainer.getTotalAmount()).isEqualByComparingTo(fromContainerBeforeTransferAmount.subtract(transferAmount));
    }

    @DataProvider
    public static Object[][] notAchievableCoinsWithTransferAmount() {
        return new Object[][]{
            {newArrayList(Coin._5, Coin._5, Coin._1), BigDecimal.valueOf(3)},
            {newArrayList(Coin._02, Coin._02, Coin._02), BigDecimal.valueOf(0.3)},
            {newArrayList(Coin._01, Coin._5, Coin._2), BigDecimal.valueOf(2.2)},
            {newArrayList(Coin._1, Coin._1, Coin._05, Coin._05, Coin._01), BigDecimal.valueOf(2.2)}
        };
    }

    @Test(dataProvider = "notAchievableCoinsWithTransferAmount")
    public void should_not_transfer_coins_to_other_container_by_given_amount(Collection<Coin> coins,  BigDecimal transferAmount){
        //given
        CoinContainer fromContainer = new CoinContainer();
        coins.forEach(fromContainer::add);
        BigDecimal fromContainerBeforeTransferAmount = fromContainer.getTotalAmount();
        CoinContainer toContainer = new CoinContainer();

        //when
        assertThatThrownBy(() -> fromContainer.transferCoinsByAmountTo(transferAmount, toContainer))
            .isInstanceOf(CantGiveTheChangeException.class);

        //then
        assertThat(toContainer.getTotalAmount()).isEqualByComparingTo(ZERO);
        assertThat(fromContainer.getTotalAmount()).isEqualByComparingTo(fromContainerBeforeTransferAmount);
    }
}
