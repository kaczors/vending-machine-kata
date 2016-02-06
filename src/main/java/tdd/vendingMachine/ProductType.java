package tdd.vendingMachine;

import java.math.BigDecimal;

public enum ProductType {

    COCA_COLA_025L(BigDecimal.valueOf(2.30)),
    COCA_COLA_05L(BigDecimal.valueOf(3.00)),
    CHOCOLATE_BAR(BigDecimal.valueOf(1.20)),
    MINERAL_WATER_033L(BigDecimal.valueOf(1.70)),
    CHOCOLATE(BigDecimal.valueOf(3.40));

    private final BigDecimal price;

    ProductType(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
