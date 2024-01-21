package models;

import java.util.ArrayList;
import java.util.List;

public class Product {

    // Class variables
    public static int nbProducts = 0;
    public static List<Product> listProducts = new ArrayList<>();

    // Member variables
    int id;
    ProductType productType;
    double price;

    public Product(ProductType type) {
        id = ++nbProducts;
        productType = type;
        price = type.standardPrice * (1 + (Math.random()*0.3 - 0.15));
    }

    @Override
    public String toString() {
        String priceStr = String.format("%.2f", price);
        return "[" + String.valueOf(id) + "] " + productType.toString() + " (" + priceStr + "€)";
    }

    public ProductType getProductType() {
        return productType;
    }
}
