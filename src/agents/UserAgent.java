package agents;

import behaviors.SeekingAdviceBehaviour;
import gui.UserAgentWindow;
import jade.core.AID;
import jade.core.AgentServicesTools;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
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


        // List
        window.addProductsToCombo(products);

        println("Je suis l'agent " + getLocalName());

//        addBehaviour(seekingAdviceBehaviour());

        ////////



//        addBehaviour(new SeekingAdviceBehaviour(this, window, cfpMessage));

        ////////

        // Repair cafes

    }

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
            //TODO: choose behaviour depending on knowledge level

            addBehaviour(new SeekingAdviceBehaviour(this, window, prepareMessage()));

            window.setButtonActivated(false);
        }
    }

    @Override
    protected void println(String text) {
        window.println(text);
    }
}
