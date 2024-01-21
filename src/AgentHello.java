import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.AgentWindowed;
import jade.gui.GuiEvent;
import jade.gui.SimpleWindow4Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

import java.awt.*;

import static java.lang.System.out;

@Deprecated
public class AgentHello extends AgentWindowed {

    public static final int STANDBY = 0;
    public static final int CFP = 1;

    private int state = STANDBY;
    private String texteHello;
    private DFAgentDescription repairCafeDesc = new DFAgentDescription();
    private DFAgentDescription[] repairCafes;

    // Initialisation
    protected void setup() {

        // Setup window

        window = new SimpleWindow4Agent(getLocalName(), this);
        window.setBackgroundTextColor(Color.green);
        println("I am " + getLocalName() + " !");
        window.setButtonActivated(true);

        // Define description used to seek repair cafes
        ServiceDescription repairCafeService = new ServiceDescription();
        repairCafeService.setType("repairCafe");
        repairCafeDesc.addServices(repairCafeService);

        // Search repair cafes
        try {
            repairCafes = DFService.search(this, repairCafeDesc);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        var cfpMessage = new ACLMessage(ACLMessage.CFP);
        cfpMessage.setContent("I am looking for advice about __.");

        for (DFAgentDescription repairCafe:repairCafes) {
            cfpMessage.addReceiver(repairCafe.getName());
        }

        // Behaviours

        FSMBehaviour fsmBehaviour = new FSMBehaviour(this);
//        fsmBehaviour.


        addBehaviour(new WakerBehaviour(this, 5000) {

            @Override
            public void onStart() {
                println("Behaviour : WakerBehaviour (5000ms)");
            }

            @Override
            protected void onWake() {
                println("woke up !");
                addBehaviour(new AchieveREInitiator(myAgent, cfpMessage) {


                    int hangingCafes = repairCafes.length;

                    @Override
                    public void onStart() {
                        println("Behaviour : AchieveREInitiator");
                        println("hangingCafes : " + hangingCafes);
                    }

                    @Override
                    protected void handleAgree(ACLMessage agree) {
                        out.println("Received agree message from " + agree.getSender());
                    }


                    @Override
                    protected void handleInform(ACLMessage inform) {
                        out.println("Received inform message from " + inform.getSender());
                        out.println("Content of the inform message : " + inform.getContent());
                    }
                });
            }
        });



    }

    @Override
    protected void onGuiEvent(GuiEvent evt) {
        if (evt.getType() == SimpleWindow4Agent.OK_EVENT) {
            if (state == STANDBY) {
                window.setButtonActivated(false);
                state = CFP;
            }
        }
    }

    // Adieu

    @Override
    protected void takeDown() {
        out.println("Agent " + getLocalName() + " quitte la plateforme.");
    }
}
