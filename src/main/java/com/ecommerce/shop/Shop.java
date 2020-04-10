package com.ecommerce.shop;

import com.ecommerce.entity.Product;
import com.ecommerce.utils.ProductsParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Shop {
    private ArrayList<Product> allProducts;
    public static String PRODUCTS_DATA_FILE = "src/main/resources/product-data.csv";
    private Random random;


    public void loadProducts() {
        try {
             allProducts = ProductsParser.parseData(PRODUCTS_DATA_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listProducts() {
        if(allProducts!=null){
            allProducts.stream().forEach(product -> System.out.println(product.productDetails()));
        }
    }

     /**
     * Add a product to the Basket
     * @param productName e.g. Banana
     * @param productPrice e.g. £10.00
     */
    public void addProductToBasket(String productName, String productPrice) {
        // TODO Exercise 2a - feature to add Products to the basket

        int productId = generateProductId();
        Product product = Product.builder()
                            .productId(productId)
                            .productName(productName)
                            .priceForDisplay(productPrice)
                            .build();
        if(allProducts != null) {
            allProducts.add(product);
            System.out.println(String.format("Product [%d] Added successfully", productId));
        }
    }

    private int generateProductId() {
        if(random == null) {
            random = new Random();
        }

        return random.nextInt(100);
    }

    /**
     * Remove a product from the Basket
     */
    public void removeProductFromBasket(int productId) {
        // TODO Exercise 2b - feature to remove Products from the basket

        if(allProducts != null) {
            Optional<Product> productOptional = allProducts.stream()
                                                            .filter(product -> product.getProductId().intValue() == productId)
                                                            .findFirst();

            if(productOptional.isPresent()) {
                allProducts.remove(productOptional.get());
                System.out.println(String.format("Product [%d] removed successfully", productId));
            }
        }
    }

    /**
     * Return the total value of the products in the basket
     */
    public String getTotal() {
        String totalPrice = null;
        if(allProducts != null) {
            double totalValue = allProducts.stream().mapToDouble(Product::getPrice).sum();

            String str = String.format("%1.2f", totalValue);
            totalPrice = "£"+Double.valueOf(str);
        }
        System.out.println(String.format("Total price is [%s] ", totalPrice));
        return totalPrice;
    }

}
