package behaviours;

import agents.UserAgent;
import gui.UserAgentWindow;
import jade.core.AID;
import jade.core.Agent;
import jade.core.AgentServicesTools;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class BreakdownFSMBehaviour extends FSMBehaviour {

    private UserAgentWindow window;

    public BreakdownFSMBehaviour(Agent a, UserAgentWindow w) {
        super(a);
        window = w;
    }

    @Override
    public void onStart() {
//        registerFirstState(new OneShotBehaviour() {
//            @Override
//            public int onEnd() {
//                myAgent.
//            }
//        }, "DoesUserHaveKnowledge");
        registerFirstState(new DoIHaveKnowledgeBehaviour((UserAgent) myAgent), "DoesUserHaveKnowledge");
        registerState(new SeekingAdviceBehaviour(myAgent, window, prepareMessage()), "SeekingAdvice");
        registerLastState(new OneShotBehaviour(myAgent) {
            @Override
            public void action() {
                window.println("FSM Behaviour reached last state.");
            }
        }, "EndState");

        registerTransition("DoesUserHaveKnowledge", "SeekingAdvice", 0);
        registerTransition("DoesUserHaveKnowledge", "EndState", 1);
        registerTransition("SeekingAdvice", "EndState", 0);
//        registerTransition("");
        super.onStart();
    }

    /**
     * Returns a CFP message addressed to all repair cafes found.
     * @return the created ACLMessage.
     */
    private ACLMessage prepareMessage() {
        AID[] repairCafes = AgentServicesTools.searchAgents(myAgent, "repairCafe", "adviceAndRepair");

        ACLMessage cfpMessage = new ACLMessage(ACLMessage.CFP);
        cfpMessage.setContent(window.getComboProduct().getProductType().toString());

        for (AID aid:repairCafes) {
            cfpMessage.addReceiver(aid);
            window.println("\t" + aid.getLocalName());
        }

        return cfpMessage;
    }
}
