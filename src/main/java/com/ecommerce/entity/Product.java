package com.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
public class Product {

  private Integer productId;
  private String productName;
  private String priceForDisplay;

  public String productDetails() {
    return "Product{" +
            "productId=" + productId +
            ", productName='" + productName + '\'' +
            ", price='" + priceForDisplay + '\'' +
            '}';
  }

  private double getPriceValue(String priceInDisplay) {

    String tempPriceInDisplay = priceInDisplay.trim();
    Float price = Float.parseFloat(tempPriceInDisplay.substring(1, tempPriceInDisplay.length()));

    BigDecimal priceValue = BigDecimal.valueOf(price);

    return priceValue.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();

  }

  public double getPrice() {
    if(StringUtils.isBlank(priceForDisplay)) {
      throw new RuntimeException();
    }

    return getPriceValue(priceForDisplay);
  }

}
