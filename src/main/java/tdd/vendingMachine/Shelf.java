package tdd.vendingMachine;

import tdd.vendingMachine.validation.exception.BusinessRuleValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

class Shelf {

    private List<Product> products = newArrayList();

    public Optional<ProductType> getProductType() {
        return products.stream().findAny().map(Product::getProductType);
    }

    public void addProduct(Product product) {
        checkProductTypeValid(product);
        products.add(product);
    }

    public boolean isNotEmpty(){
        return !products.isEmpty();
    }

    BigDecimal getProductPrice() {
        return getProductType()
            .map(ProductType::getPrice)
            .orElseThrow(() -> new RuntimeException("Shelf is empty"));
    }

    private void checkProductTypeValid(Product product) {
        if (getProductType().isPresent()) {
            ProductType productType = product.getProductType();
            ProductType shelfProductType = getProductType().get();

            if (!shelfProductType.equals(productType)) {
                throw new BusinessRuleValidationException("Cannot add product with product type: " + productType + " to shelf of type: " + shelfProductType);
            }
        }
    }

    static Shelf empty(){
        return new Shelf();
    }

}