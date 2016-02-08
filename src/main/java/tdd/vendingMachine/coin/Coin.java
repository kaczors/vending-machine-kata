package tdd.vendingMachine.coin;

import java.math.BigDecimal;
import java.util.Comparator;

public enum Coin {

    _01(new BigDecimal("0.1")),
    _02(new BigDecimal("0.2")),
    _05(new BigDecimal("0.5")),
    _1(new BigDecimal("1")),
    _2(new BigDecimal("2")),
    _5(new BigDecimal("5")),
    _10(new BigDecimal("10")),
    _20(new BigDecimal("20"));

    public final static Comparator<Coin> DESCENDING_VALUE_ORDER = (c1, c2) -> c2.getAmount().compareTo(c1.getAmount());

    private final BigDecimal value;

    Coin(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getAmount() {
        return value;
    }

    @Override
    public String toString(){
        return value.toString();
    }

}
