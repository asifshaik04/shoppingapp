package com.ecommerce.utils;

import com.ecommerce.entity.Product;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

public class ProductsParser {

  public static final String PRODUCT_ID = "#productId";

  public static ArrayList<Product> parseData(String productsFilePath) throws IOException {

    if(StringUtils.isBlank(productsFilePath)){
      return null;
    }

    ArrayList<Product> products = new ArrayList<Product>();

    CSVReader reader = new CSVReader(new FileReader(productsFilePath));
    String [] nextLine;
    while ((nextLine = reader.readNext()) != null) {
      if(isNotAHeaderColumn(nextLine[0])) {

        Product product = Product.builder()
                .productId(new Integer(nextLine[0]))
                .productName(nextLine[1])
                .priceForDisplay(nextLine[2])
                .build();


        products.add(product);
      }
    }
    return products;
  }

  private static boolean isNotAHeaderColumn(String value) {
    return !value.equals(PRODUCT_ID);
  }

}
