import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        //Setup
        Properties props = new ExtendedProperties();
        props.setProperty(Profile.GUI, "true");
        props.setProperty(Profile.AGENTS,
                "agent1:AgentHello;" +
                "agent2:AgentHello"
        );
        ProfileImpl profileMain = new ProfileImpl(props);

        //Launch
        Runtime rt = Runtime.instance();
        rt.createMainContainer(profileMain);

//        String[] jadeArgs = new String[2];
//        StringBuilder sbAgents = new StringBuilder();
//        sbAgents.append("myFirstAgent:AgentHello").append(";");
//        jadeArgs[0] = "-gui";
//        jadeArgs[1] = sbAgents.toString();
//        jade.Boot.main(jadeArgs);
    }
}