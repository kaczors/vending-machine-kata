package tdd.vendingMachine.product;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

public class Product {

    private ProductType productType;

    public Product(ProductType productType) {
        this.productType = checkNotNull(productType);
    }

    public ProductType getProductType() {
        return productType;
    }

    public BigDecimal getPrice() {
        return productType.getPrice();
    }
}
