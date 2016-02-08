package tdd.vendingMachine;

import com.google.common.collect.Lists;
import org.testng.annotations.Test;
import tdd.vendingMachine.coin.Coin;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CoinTest {

    @Test
    public void should_sort_in_descending_value_order(){
        //given
        List<Coin> coins = Lists.newArrayList(Coin._01, Coin._02, Coin._05, Coin._1, Coin._2, Coin._5, Coin._20);

        //when
        coins.sort(Coin.DESCENDING_VALUE_ORDER);

        //then
        assertThat(coins).containsExactly(Coin._20, Coin._5, Coin._2, Coin._1, Coin._05, Coin._02, Coin._01);
    }

}
