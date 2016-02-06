package tdd.vendingMachine;

import java.math.BigDecimal;

public class Product {

    private ProductType productType;

    public Product(ProductType productType) {
        this.productType = productType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public BigDecimal getPrice(){
        return productType.getPrice();
    }
}
