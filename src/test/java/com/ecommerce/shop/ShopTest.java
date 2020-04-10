package com.ecommerce.shop;

import com.ecommerce.entity.Product;
import com.ecommerce.utils.ProductsParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ProductsParser.class )
public class ShopTest {

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  private Shop shop;

  @Before
  public void setUp(){
    shop = new Shop();
    PowerMockito.mockStatic(ProductsParser.class);
  }

  @Test
  public void testLoadProducts() throws IOException {
    //Given
    PowerMockito.when(ProductsParser.parseData("")).thenReturn(new ArrayList<Product>());

    //When
    shop.loadProducts();
  }

  @Test
  public void loadProductsWithException() throws IOException {
    //Given
    PowerMockito.when(ProductsParser.parseData(Shop.PRODUCTS_DATA_FILE)).thenThrow(new FileNotFoundException());
    exceptionRule.expect(RuntimeException.class);

    //When
    shop.loadProducts();

    //Then
    Mockito.verify(exceptionRule.handleAssertionErrors());
  }

  @Test
  public void listProducts() throws IOException {
    // Given
    ArrayList<Product> products = new ArrayList<>();
    Product mockProduct = Mockito.mock(Product.class);
    products.add(mockProduct);

    // When
    Mockito.when(ProductsParser.parseData(Shop.PRODUCTS_DATA_FILE)).thenReturn(products);
    shop.listProducts();

    // Then
    Mockito.verify(mockProduct, Mockito.atMost(1)).productDetails();
  }


  @Test
  public void addProduct() throws IOException {
    //Given
    ArrayList<Product> products = new ArrayList<>();
    Mockito.when(ProductsParser.parseData(Shop.PRODUCTS_DATA_FILE)).thenReturn(products);
    shop.loadProducts();
    assertEquals(products.size(), 0);

    //When
    shop.addProductToBasket( "Orange juice", "£10.00");

    //Then
    assertEquals(products.size(), 1);
    Product addedProduct = products.get(0);
    assertEquals(addedProduct.getProductName(), "Orange juice");
    assertEquals(addedProduct.getPriceForDisplay(), "£10.00");
    assertTrue(addedProduct.getPrice() == 10.0);
  }


    @Test
    public void getTotal() throws IOException {

    //Given
    ArrayList<Product> products = new ArrayList<>();
    Product product1 = Product.builder()
            .productId(new Integer(1))
            .priceForDisplay("£10.00")
            .build();
    products.add(product1);

    Product product2 = Product.builder()
            .productId(new Integer(2))
            .priceForDisplay("£50.55")
            .build();
    products.add(product2);

    Mockito.when(ProductsParser.parseData(Shop.PRODUCTS_DATA_FILE)).thenReturn(products);
    shop.loadProducts();
    assertEquals(products.size(), 2);

    //When
    String totalAmount = shop.getTotal();;

    //Then
    assertEquals(totalAmount, "£60.55");
  }

  @Test
  public void removeProduct() throws IOException {

    //Given
    ArrayList<Product> products = new ArrayList<>();
    Product product = Product.builder()
            .productId(new Integer(1))
            .build();
    products.add(product);
    Mockito.when(ProductsParser.parseData(Shop.PRODUCTS_DATA_FILE)).thenReturn(products);
    shop.loadProducts();
    assertEquals(products.size(), 1);

    //When
    shop.removeProductFromBasket(1);

    //Then
    assertEquals(products.size(), 0);
  }
}
