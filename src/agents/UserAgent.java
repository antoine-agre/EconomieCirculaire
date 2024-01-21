package agents;

import behaviours.BreakdownFSMBehaviour;
import gui.UserAgentWindow;
import jade.core.AID;
import jade.core.AgentServicesTools;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import models.Part;
import models.Product;
import models.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserAgent extends GuiAgent {

    UserAgentWindow window; // = new UserAgentWindow(getLocalName(), this);
    List<Product> products = new ArrayList<Product>();
    Product selectedProduct;
    private ACLMessage cfpMessage;
    int skill;
    Product brokenProduct = null;


    // Methods

    @Override
    protected void setup() {
        // Window setup
        window = new UserAgentWindow(getLocalName(), this);
        window.setButtonActivated(true);

        // Products
        window.println("Products:");
        Random rnd = new Random();
        for (ProductType productType:ProductType.values()) {
            if (rnd.nextBoolean()) {
                products.add(new Product(productType));
            }
        }
        if (products.isEmpty()) {
            products.add(new Product(ProductType.values()[rnd.nextInt(ProductType.values().length)]));
        }
        for (Product product:products) {
            window.println("\t" + product.toString());
        }

        // Skill
        skill = rnd.nextInt(4); //0 to 3
        window.println("My skill level is " + skill);

        // List
        window.addProductsToCombo(products);

        println("Je suis l'agent " + getLocalName());

    }

    @Deprecated
    /**
     * Returns a CFP message addressed to all repair cafes found.
     * @return the created ACLMessage.
     */
    private ACLMessage prepareMessage() {
        AID[] repairCafes = AgentServicesTools.searchAgents(this, "repairCafe", "adviceAndRepair");

        ACLMessage cfpMessage = new ACLMessage(ACLMessage.CFP);
        cfpMessage.setContent(window.getComboProduct().getProductType().toString());

        for (AID aid:repairCafes) {
            cfpMessage.addReceiver(aid);
            println("\t" + aid.getLocalName());
        }

        return cfpMessage;
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        if (guiEvent.getType() == UserAgentWindow.OK_EVENT) {

            // Cause breakdown on element
            brokenProduct = window.getComboProduct();
            brokenProduct.causeBreakdown();
            window.println("Following part had a breakdown : Part" + brokenProduct.getProductType().toString() + String.valueOf(brokenProduct.getBrokenPart()));

            addBehaviour(new BreakdownFSMBehaviour(this, window));
//            addBehaviour(new SeekingAdviceBehaviour(this, window, prepareMessage()));

            window.setButtonActivated(false);
        }
    }

    @Override
    protected void println(String text) {
        window.println(text);
    }

    public int getSkill() {
        return skill;
    }

    public Product getBrokenProduct() {
        return brokenProduct;
    }
}
