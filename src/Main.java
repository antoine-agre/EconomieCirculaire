import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import models.Product;
import models.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < 20; i++) {
            products.add(new Product(ProductType.values()[rnd.nextInt(ProductType.values().length)]));
        }

        for (Product product:products) {
            System.out.println(product.toString());
        }

        //Setup
        Properties props = new ExtendedProperties();
        props.setProperty(Profile.GUI, "true");
        props.setProperty(Profile.AGENTS,
                "agent1:agents.UserAgent;" +
                "agent2:agents.UserAgent;" +
                "cafe1:agents.RepairCafeAgent;" +
                "cafe2:agents.RepairCafeAgent;" +
                "cafe3:agents.RepairCafeAgent;" +
                "spare1:agents.SparePartStoreAgent;" +
                "spare2:agents.SparePartStoreAgent;" +
                "spare3:agents.SparePartStoreAgent;"
        );
        ProfileImpl profileMain = new ProfileImpl(props);

        //Launch
        Runtime rt = Runtime.instance();
        rt.createMainContainer(profileMain);
    }
}