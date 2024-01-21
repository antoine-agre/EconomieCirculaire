package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Product {

    // Class variables
    public static int nbProducts = 0;
    public static List<Product> listProducts = new ArrayList<>();

    // Member variables
    int id;
    ProductType productType;
    double price;
//    List<Part> parts = new ArrayList<>();
//    Part brokenPart = null;
    // -1 for no breakdown, 0-4 for severity
    int breakdownSeverity = -1;
    // 1 to productType.nbParts for part broken
    int brokenPart;

    public Product(ProductType type) {
        id = ++nbProducts;
        productType = type;
        price = type.standardPrice * (1 + (Math.random()*0.3 - 0.15));

//        List<Part> templates = new ArrayList<Part>();
//        for (Part standardPart:Part.getListParts()) {
//            if (standardPart.productType == productType) {
//                parts.add(Part.fromTemplate(standardPart));
//            }
//        }

    }

    public void causeBreakdown() {
        Random rnd = new Random();
        brokenPart = rnd.nextInt(1, productType.nbParts + 1);
        breakdownSeverity = rnd.nextInt(5); //0 to 4
//        brokenPart = parts.get(rnd.nextInt(parts.size())); //0 to 4
//        return brokenPart;
    }

//    @Deprecated
//    public void causeBreakdown(int gravity) {
//        if (0 <= gravity && gravity <= productType.getNbParts()) {brokenPart = parts.get(gravity);}
//    }

    @Override
    public String toString() {
        String priceStr = String.format("%.2f", price);
        return "[" + String.valueOf(id) + "] " + productType.toString() + " (" + priceStr + "€)";
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getBreakdownSeverity() {
        return breakdownSeverity;
    }

    public int getBrokenPart() {
        return brokenPart;
    }

    //    public List<Part> getParts() {
//        return parts;
//    }
//
//    public Part getBrokenPart() {
//        return brokenPart;
//    }
}
