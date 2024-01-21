package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum ProductType implements Serializable {

    Mouse(1, 30),
    Screen(2, 150),
    CoffeeMaker(3, 50),
    WashingMachine(4, 300),
    Dishwasher(4, 300),
    VacuumCleaner(3, 100);

    int nbParts;
    double standardPrice;

    ProductType(int nbParts, double standardPrice){
        this.nbParts = nbParts;
        this.standardPrice = standardPrice;
    }

    public int getNbParts() {
        return nbParts;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

}
