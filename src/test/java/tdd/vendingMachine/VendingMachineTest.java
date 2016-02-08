package tdd.vendingMachine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tdd.vendingMachine.coin.Coin;
import tdd.vendingMachine.hardware.CoinContainer;
import tdd.vendingMachine.hardware.Display;
import tdd.vendingMachine.hardware.ProductStorage;
import tdd.vendingMachine.hardware.VendingMachineHardware;
import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.validation.CoinEntryValidator;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static tdd.vendingMachine.MessageFormats.AMOUNT_MESSAGE;
import static tdd.vendingMachine.MessageFormats.CANT_GIVE_THE_CHANGE_MESSAGE;
import static tdd.vendingMachine.MessageFormats.WELCOME_MESSAGE;
import static tdd.vendingMachine.product.ProductType.CHOCOLATE;
import static tdd.vendingMachine.product.ProductType.CHOCOLATE_BAR;
import static tdd.vendingMachine.product.ProductType.COCA_COLA_05L;
import static tdd.vendingMachine.product.ProductType.MINERAL_WATER_033L;

public class VendingMachineTest {

    private static final int SAMPLE_SHELF_NUMBER = 1;

    VendingMachine vendingMachine;

    @BeforeMethod
    private void setUp() {
        vendingMachine = new VendingMachine(
            new VendingMachineHardware(
                new CoinContainer(),
                new CoinContainer(),
                new CoinContainer(),
                new CoinContainer(),
                new Display(WELCOME_MESSAGE),
                new ProductStorage(),
                newArrayList()
            ), new CoinEntryValidator());
    }

    @Test
    public void should_show_welcome_message_on_start() {
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(WELCOME_MESSAGE);
    }

    @Test
    public void should_have_empty_output_coin_tray_on_start() {
        assertThat(vendingMachine.getOutputTrayCoins()).isEmpty();
    }

    @Test
    public void should_have_empty_output_product_tray_on_start() {
        assertThat(vendingMachine.getOutputProductsTray()).isEmpty();
    }

    @DataProvider
    public static Object[][] unsupportedCoins() {
        return new Object[][]{
            {Coin._10}, {Coin._20}
        };
    }

    @Test(dataProvider = "unsupportedCoins")
    public void should_reject_unsupported_coin(Coin unsupportedCoin) {
        //when
        vendingMachine.insertCoin(unsupportedCoin);

        //then
        assertThat(vendingMachine.getOutputTrayCoins()).containsExactly(unsupportedCoin);
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(WELCOME_MESSAGE);
    }

    @DataProvider
    public static Object[][] coinsAndExpectedSum() {
        return new Object[][]{
            {newArrayList(Coin._1, Coin._05), "1,50"},
            {newArrayList(Coin._01, Coin._02), "0,30"},
            {newArrayList(Coin._1, Coin._1, Coin._1), "3,00"}
        };
    }

    @Test(dataProvider = "coinsAndExpectedSum")
    public void should_show_sum_of_inserted_coins_before_product_is_selected(List<Coin> coins, String expectedMessage) {
        //when
        coins.forEach(vendingMachine::insertCoin);

        //then
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(expectedMessage);
    }

    @DataProvider
    public static Object[][] products() {
        return new Object[][]{{new Product(CHOCOLATE)}, {new Product(COCA_COLA_05L)}};
    }

    @Test(dataProvider = "products")
    public void should_display_product_price_after_shelf_number_is_selected(Product product) {
        //given
        vendingMachine.addProduct(SAMPLE_SHELF_NUMBER, product);

        //when
        vendingMachine.selectShelf(SAMPLE_SHELF_NUMBER);

        //then
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(formatAmountMessage(product.getPrice()));
    }

    @Test(dataProvider = "products")
    public void should_display_amount_that_must_be_added_to_cover_product_price_after_product_is_chosen(Product product) {
        //given
        vendingMachine.addProduct(SAMPLE_SHELF_NUMBER, product);
        vendingMachine.selectShelf(SAMPLE_SHELF_NUMBER);
        BigDecimal expectedAmount = product.getPrice().subtract(Coin._1.getAmount());

        //when
        vendingMachine.insertCoin(Coin._1);

        //then
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(formatAmountMessage(expectedAmount));
    }

    @Test
    public void should_return_coins_and_reset_machine_state_on_cancel() {
        //given
        vendingMachine.addProduct(SAMPLE_SHELF_NUMBER, new Product(MINERAL_WATER_033L));
        vendingMachine.selectShelf(SAMPLE_SHELF_NUMBER);
        vendingMachine.insertCoin(Coin._01);
        vendingMachine.insertCoin(Coin._02);

        //when
        vendingMachine.cancel();

        //then
        assertThat(vendingMachine.getOutputTrayCoins()).containsOnly(Coin._01, Coin._02);
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(WELCOME_MESSAGE);
        assertThat(vendingMachine.getOutputProductsTray()).isEmpty();
    }

    @Test
    public void should_return_coins_and_display_warning_message_when_machine_cant_give_the_change() {
        //given
        vendingMachine.addProduct(SAMPLE_SHELF_NUMBER, new Product(MINERAL_WATER_033L));

        //when
        vendingMachine.selectShelf(SAMPLE_SHELF_NUMBER);
        vendingMachine.insertCoin(Coin._5);

        //then
        assertThat(vendingMachine.getOutputTrayCoins()).containsOnly(Coin._5);
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(CANT_GIVE_THE_CHANGE_MESSAGE);
        assertThat(vendingMachine.getOutputProductsTray()).isEmpty();
    }

    @Test
    public void should_drop_product_and_the_change() {
        //given
        Product product = new Product(CHOCOLATE_BAR);
        vendingMachine.addProduct(SAMPLE_SHELF_NUMBER, product);
        vendingMachine.addCoinsToCassette(newArrayList(Coin._02, Coin._02, Coin._01, Coin._01));

        //when
        vendingMachine.selectShelf(SAMPLE_SHELF_NUMBER);
        vendingMachine.insertCoin(Coin._1);
        vendingMachine.insertCoin(Coin._05);

        //then
        assertThat(vendingMachine.getOutputTrayCoins()).containsOnly(Coin._01, Coin._02);
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(WELCOME_MESSAGE);
        assertThat(vendingMachine.getOutputProductsTray()).containsOnly(product);
    }

    @Test
    public void after_failure_and_next_coin_insertion_should_display_inserted_amount() {
        //given
        Product product = new Product(CHOCOLATE_BAR);
        vendingMachine.addProduct(SAMPLE_SHELF_NUMBER, product);
        vendingMachine.selectShelf(SAMPLE_SHELF_NUMBER);
        vendingMachine.insertCoin(Coin._2);
        // buy failed

        //when
        vendingMachine.insertCoin(Coin._5);

        //then
        assertThat(vendingMachine.getOutputTrayCoins()).containsOnly(Coin._2);
        assertThat(vendingMachine.getMessageFromDisplay()).isEqualTo(formatAmountMessage(Coin._5.getAmount()));
        assertThat(vendingMachine.getOutputProductsTray()).isEmpty();
    }

    private String formatAmountMessage(BigDecimal amount) {
        return String.format(AMOUNT_MESSAGE, amount);
    }
}

