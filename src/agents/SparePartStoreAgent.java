package agents;

import jade.gui.AgentWindowed;
import jade.gui.SimpleWindow4Agent;
import models.Part;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SparePartStoreAgent extends AgentWindowed {

    // Variables
    List<Part> parts;

    @Override
    protected void setup() {

        // Setup window
        window = new SimpleWindow4Agent(getLocalName(), this);
        window.setBackgroundTextColor(Color.orange);
        println("I am a spare parts shop.");

        // Create parts stock
        parts = new ArrayList<Part>();
        Random rnd = new Random();
        for (Part part:Part.getListParts()) {
            if (rnd.nextBoolean()) {parts.add(new Part(part.getProductType(), part.getPrice()*(1 + (Math.random()*0.3 - 0.15)), part.getId()));}
        }
        if (parts.isEmpty()) {parts.add(Part.getListParts().get(rnd.nextInt(Part.getListParts().size())));}
        println("My inventory :");
        parts.forEach((Part part) -> println('\t' + part.toString()));


    }
}
