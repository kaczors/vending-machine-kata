package tdd.vendingMachine.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberUtilsTest {

    @DataProvider
    public static Object[][] firstNumberLowerThanSecond() {
        return new Object[][]{
            {new BigDecimal("1.20"), new BigDecimal("1.21")},
            {new BigDecimal("3"), new BigDecimal("4")},
            {new BigDecimal("10"), new BigDecimal("20")}};
    }

    @Test(dataProvider = "firstNumberLowerThanSecond")
    public void should_not_be_equal_or_greater_than(BigDecimal number1, BigDecimal number2) {
        assertThat(NumberUtils.isEqualOrGreaterThan(number1, number2)).isFalse();
    }

    @DataProvider
    public static Object[][] firstNumberEqualOrGreaterThanSecond() {
        return new Object[][]{
            {new BigDecimal("1.20"), new BigDecimal("1.20")},
            {new BigDecimal("3"), new BigDecimal("2.99")},
            {new BigDecimal("10"), new BigDecimal("0")}};
    }

    @Test(dataProvider = "firstNumberEqualOrGreaterThanSecond")
    public void should_be_equal_or_greater_than(BigDecimal number1, BigDecimal number2) {
        assertThat(NumberUtils.isEqualOrGreaterThan(number1, number2)).isTrue();
    }

    @DataProvider
    public static Object[][] equalNumbers() {
        return new Object[][]{
            {new BigDecimal("1.20"), new BigDecimal("1.20")},
            {new BigDecimal("3"), new BigDecimal("3.00")},
            {new BigDecimal("11.0"), new BigDecimal("11.000")}};
    }

    @Test(dataProvider = "equalNumbers")
    public void should_be_equal(BigDecimal number1, BigDecimal number2){
        assertThat(NumberUtils.isEqualByComparingTo(number1, number2)).isTrue();
    }

    @DataProvider
    public static Object[][] notEqualNumbers() {
        return new Object[][]{
            {new BigDecimal("1.21"), new BigDecimal("1.20")},
            {new BigDecimal("3"), new BigDecimal("3.01")},
            {new BigDecimal("11.1"), new BigDecimal("11.000")}};
    }

    @Test(dataProvider = "notEqualNumbers")
    public void should_not_be_equal(BigDecimal number1, BigDecimal number2){
        assertThat(NumberUtils.isEqualByComparingTo(number1, number2)).isFalse();
    }
}
