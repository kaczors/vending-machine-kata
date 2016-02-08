package tdd.vendingMachine;

import org.testng.annotations.Test;
import tdd.vendingMachine.hardware.Shelf;
import tdd.vendingMachine.product.Product;
import tdd.vendingMachine.product.ProductType;
import tdd.vendingMachine.validation.exception.ApplicationException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static tdd.vendingMachine.product.ProductType.CHOCOLATE;
import static tdd.vendingMachine.product.ProductType.COCA_COLA_025L;
import static tdd.vendingMachine.product.ProductType.COCA_COLA_05L;
import static tdd.vendingMachine.product.ProductType.MINERAL_WATER_033L;

public class ShelfTest {

    private static final Product SAMPLE_PRODUCT = new Product(MINERAL_WATER_033L);

    @Test
    public void should_determine_type_based_on_contained_product(){
        //given
        Shelf shelf = new Shelf();
        shelf.addProduct(new Product(CHOCOLATE));

        //when
        Optional<ProductType> productType = shelf.getProductType();

        //then
        assertThat(productType).contains(CHOCOLATE);
    }

    @Test
    public void should_return_empty_product_type_when_no_product_on_shelf(){
        //given
        Shelf shelf = new Shelf();

        //when
        Optional<ProductType> productType = shelf.getProductType();

        //then
        assertThat(productType).isEmpty();
    }

    @Test(expectedExceptions = ApplicationException.class)
    public void should_throw_exception_when_attempt_to_add_product_with_invalid_product_type(){
        //given
        Shelf shelf = new Shelf();
        shelf.addProduct(new Product(CHOCOLATE));

        //when
        shelf.addProduct(new Product(COCA_COLA_05L));

        //then exception expected
    }

    @Test
    public void should_determine_product_price_based_on_contained_product(){
        //given
        Shelf shelf = new Shelf();
        shelf.addProduct(new Product(COCA_COLA_025L));

        //when
        BigDecimal productPrice = shelf.getProductPrice();

        //then
        assertThat(productPrice).isEqualByComparingTo(COCA_COLA_025L.getPrice());
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Shelf is empty")
    public void should_throw_exception_when_no_product_on_shelf(){
        //given
        Shelf shelf = new Shelf();

        //when
        BigDecimal productPrice = shelf.getProductPrice();

        //then exception expected
    }

    @Test
    public void should_drop_product_to_given_destination(){
        //given
        Shelf shelf = new Shelf();
        shelf.addProduct(SAMPLE_PRODUCT);
        Collection<Product> destination = newArrayList();

        //when
        shelf.dropProductTo(destination);

        //then
        assertThat(destination).containsOnly(SAMPLE_PRODUCT);
        assertThat(shelf.isEmpty()).isTrue();
    }
}
