import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.proto.states.MsgReceiver;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class AgentHello extends Agent {
    private String texteHello;

    // Initialisation
    protected void setup() {
        texteHello = "Bonjour, je suis un agent.";
        out.println("[" + getLocalName() + "] " + texteHello);
        out.println("\tMon adresse est : " + getAID());

//        addBehaviour(new CountdownBehavior());

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    out.println("[" + myAgent.getLocalName() + "] Message reçu de " + message.getSender().getLocalName() + " :");
                    out.println(message.getContent());
                }
                block();
            }
        });

        if (getLocalName().equals("agent1")) {
            out.println("[" + getLocalName() + "] Je suis bien l'agent1 !");
            addBehaviour(new WakerBehaviour(this, 10000) {
                @Override
                protected void onWake() {
                    out.println("[" + myAgent.getLocalName() + "] Sending a message to agent2");

                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    message.addReceiver(new AID("agent2", AID.ISLOCALNAME));
                    message.setContent("Bonjoure agent2, ici agent1.");
                    send(message);
                }
            });
        }

    }

    // Adieu

    @Override
    protected void takeDown() {
        out.println("Agent " + getLocalName() + " quitte la plateforme.");
    }
}
