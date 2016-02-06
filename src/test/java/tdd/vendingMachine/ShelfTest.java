package tdd.vendingMachine;

import org.testng.annotations.Test;
import tdd.vendingMachine.validation.exception.BusinessRuleValidationException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tdd.vendingMachine.ProductType.CHOCOLATE;
import static tdd.vendingMachine.ProductType.COCA_COLA_025L;
import static tdd.vendingMachine.ProductType.COCA_COLA_05L;

public class ShelfTest {

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

    @Test(expectedExceptions = BusinessRuleValidationException.class, expectedExceptionsMessageRegExp = "Cannot add product with product type.*")
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
}
