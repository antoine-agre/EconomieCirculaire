package behaviours;

import gui.UserAgentWindow;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SeekingAdviceBehaviour extends ContractNetInitiator {

//    private DFAgentDescription repairCafeDesc = new DFAgentDescription();
//    private AID[] repairCafes;

    private LocalDateTime earliestRDV = LocalDateTime.MAX;
    private AID bestCafe = null;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private UserAgentWindow window;

    public SeekingAdviceBehaviour(Agent a, UserAgentWindow w, ACLMessage msg) {
        super(a, msg);
        window = w;
    }

    @Override
    public void onStart() {
        window.println("Starting ContractNetInitiator.");
        super.onStart();
    }

    @Override
    public int onEnd() {
        window.println("Ending ContractNetInitiator.");
        return 0;
    }

    @Override
    protected void handlePropose(ACLMessage propose, List<ACLMessage> acceptances) {
        window.println("Received proposition from " + propose.getSender().getName() + " :");
        window.println("\t" + propose.getContent());

        LocalDateTime proposed = LocalDateTime.parse(propose.getContent(), dateTimeFormatter);
        if (proposed.isBefore(earliestRDV)) {
            earliestRDV = proposed;
            bestCafe = propose.getSender();
            window.println(propose.getSender().getName() + " is the earliest proposal so far!");
        }

        super.handlePropose(propose, acceptances);
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        window.println("Received refusal from " + refuse.getSender().getName());
        super.handleRefuse(refuse);
    }

    @Override
    protected void handleAllResponses(List<ACLMessage> responses, List<ACLMessage> acceptances) {

        for (ACLMessage response:responses) {
            ACLMessage answer = response.createReply();
            if (response.getSender() == bestCafe) {
                answer.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            } else {
                answer.setPerformative(ACLMessage.REJECT_PROPOSAL);
            }
            acceptances.add(answer);
        }

        window.println("Proposal accepted from " + bestCafe.getName());
        window.println("Fixed RDV : " + earliestRDV.toString());

        super.handleAllResponses(responses, acceptances);
    }

    //    @Override
//    protected void handleAgree(ACLMessage agree) {
//        window.println("Received agree message from " + agree.getSender());
//    }
//
//    @Override
//    protected void handleRefuse(ACLMessage refuse) {
//        window.println("Received refuse message from " + refuse.getSender());
//    }
//
//    @Override
//    protected void handleAllResponses(List<ACLMessage> responses) {
//
//        int bestValue = Integer.MIN_VALUE;
//        AID bestSender;
//
//        for (ACLMessage response:responses) {
//            int value = Integer.parseInt(response.getContent().substring(8));
//            if (value > bestValue) {
//                bestValue = value;
//                bestSender = response.getSender();
//            }
//        }
//
//        super.handleAllResponses(responses);
//    }
//
//    @Override
//    protected void handleInform(ACLMessage inform) {
//        window.println("Received inform message from " + inform.getSender());
//        window.println("Content of the inform message : " + inform.getContent());
//    }

}
