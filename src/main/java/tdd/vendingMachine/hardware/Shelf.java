package tdd.vendingMachine.hardware;

import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.product.ProductType;
import tdd.vendingMachine.validation.exception.ApplicationException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

public class Shelf {

    private List<Product> products = newArrayList();

    public Optional<ProductType> getProductType() {
        return products.stream().findAny().map(Product::getProductType);
    }

    public void addProduct(Product product) {
        checkProductTypeValid(product);
        products.add(product);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public BigDecimal getProductPrice() {
        return getProductType()
            .map(ProductType::getPrice)
            .orElseThrow(() -> new RuntimeException("Shelf is empty"));
    }

    private void checkProductTypeValid(Product product) {
        if (getProductType().isPresent()) {
            ProductType productType = product.getProductType();
            ProductType shelfProductType = getProductType().get();

            if (!shelfProductType.equals(productType)) {
                throw new ApplicationException("Product type: " + productType + " does not match to shelf product type: " + shelfProductType);
            }
        }
    }

    public void dropProductTo(Collection<Product> destination) {
        destination.add(products.remove(0));
    }

    public static Shelf empty() {
        return new Shelf();
    }

}
