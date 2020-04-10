package com.ecommerce.utils;

import com.ecommerce.entity.Product;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ProductsParserTest {

  private ProductsParser productsParser;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void setUp(){
    productsParser = new ProductsParser();
  }


  @Test
  public void parseData() throws IOException {
    //Given
    String fileName = "src/main/resources/product-data.csv";

    //When
    ArrayList<Product> products = productsParser.parseData(fileName);

    //Then
    assertNotNull(products);
    assertEquals(products.size(), 7);
  }

  @Test
  public void parseDataWithNoFileName() throws IOException {
    //Given and When
    ArrayList<Product> products = productsParser.parseData(null);

    //Then
    assertNull(products);
  }

  @Test
  public void parseDataWithRuntimeError() throws IOException {
    // Given
    exceptionRule.expect(FileNotFoundException.class);

    //When
    exceptionRule.expectMessage("src\\main\\resources\\somefile.csv (The system cannot find the file specified)");
    String fileName = "src/main/resources/somefile.csv";
    productsParser.parseData(fileName);

    //Then
    Mockito.verify(exceptionRule.handleAssertionErrors());
  }

}
