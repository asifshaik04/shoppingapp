package com.ecommerce.entity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ProductTest {

    private Product product;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        product = Product.builder().productId(1).build();
    }

    @Test
    public void testProductDetails() {
        //Given and When
        Product product = Product.builder()
                .productId(new Integer(1))
                .productName("Apples")
                .priceForDisplay("£10.00")
                .build();

        // Then
        assertEquals(product.productDetails(), "Product{productId=1, productName='Apples', price='£10.00'}");
    }

    @Test
    public void testGetPrice() {
        //Given and When
        product.setPriceForDisplay("£10.00");
        // Then
        assertTrue(product.getPrice() == 10.0);

        // Given and When
        product.setPriceForDisplay("£100.00");
        // Then
        assertTrue(product.getPrice() == 100.0);

        // Given
        product.setPriceForDisplay("£50.55");
        // When and Then
        assertTrue(product.getPrice() == 50.55);

    }

    @Test
    public void testGetPrice_ExpectedException() {
        product.setPriceForDisplay(null);
        exceptionRule.expect(RuntimeException.class);

        // When
        product.getPrice();
    }
}
