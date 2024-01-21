package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Part {

    // Class variables
    /** List of "template" parts, used by stores to fill their inventory. */
    public static List<Part> listParts;

    // Member variables
    ProductType productType;
    /** Standard price if this part is a template in listParts, otherwise price of the individual part. */
    Double price;
    String id;

    public Part(ProductType type, Double standardPrice, String id) {
        this.productType = type;
        this.price = standardPrice;
        this.id = id;
    }

    public static Part fromTemplate(Part template) {
        Random rnd = new Random();
        Double price = template.getPrice() * (1 + (Math.random()*0.3 - 0.15));;
        return new Part(template.getProductType(), price, template.getId());
    }

//    public Part(Part part) {
//        this.productType = part.productType;
//        this.standardPrice = part.standardPrice;
//    }

    // Methods

    @Override
    public String toString() {
        String priceStr = String.format("%.2f", price);
        return "(" + id + " - " + priceStr + "€)";
    }


    // Getters/Setters

    public static List<Part> getListParts() {
        if (listParts == null) {

            listParts = new ArrayList<Part>();
            for (ProductType productType : ProductType.values()) {
                for (int i = 1; i <= productType.getNbParts(); i++) {
                    listParts.add(new Part(productType, Math.pow(2.0/3.0,i)*productType.getStandardPrice(), "Part"+productType.name()+i));
                }
            }
        }
        return listParts;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }
}
