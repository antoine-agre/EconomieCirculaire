package agents;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;
import jade.gui.AgentWindowed;
import jade.gui.SimpleWindow4Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import jade.proto.ContractNetResponder;
import jade.proto.SimpleAchieveREResponder;
import models.ProductType;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RepairCafeAgent extends AgentWindowed {

    // Variables
    List<ProductType> specialties;
    private Random random = new Random();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Methods

    @Override
    protected void setup() {

        // Declare service
        DFAgentDescription agentDesc = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        service.setType("repairCafe");
        service.setName("adviceAndRepair");
        agentDesc.addServices(service);

        try {
            DFService.register(this, agentDesc);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Setup window
        window = new SimpleWindow4Agent(getLocalName(), this);
        window.setBackgroundTextColor(Color.yellow);
        println("I am a repair cafe.");

        // Choose specialties randomly
        specialties = new ArrayList<ProductType>();
        for (ProductType type:ProductType.values()) {
            if (random.nextBoolean()) {specialties.add(type);}
        }
        if (specialties.isEmpty()) {specialties.add(ProductType.values()[random.nextInt(ProductType.values().length)]);}
        println("My specialties are :");
        specialties.forEach((ProductType type) -> println('\t' + type.name()));

        // Add behaviour

        MessageTemplate model = MessageTemplate.MatchPerformative(ACLMessage.CFP);

        addBehaviour(new ContractNetResponder(this, model) {

            @Override
            protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
                window.println("Received request for advice from " + cfp.getSender().getName());

                ACLMessage message = cfp.createReply();

                ProductType productType = ProductType.valueOf(cfp.getContent());
                if (!specialties.contains(productType)) {
                    window.println("Specialty not covered, sending refusal.");
                    message.setPerformative(ACLMessage.REFUSE);
                    return message;
                }

                message.setPerformative(ACLMessage.PROPOSE);

                LocalDateTime rdvTime = LocalDateTime.now().plusDays(random.nextInt(1, 6));
                int hour = random.nextInt(9, 18);
                rdvTime = rdvTime.withHour(hour);
                int minute = random.nextInt(2) * 30;
                rdvTime = rdvTime.withMinute(minute);
                message.setContent(rdvTime.format(dateTimeFormatter));

                window.println("Sending positive response to " + message.getAllReceiver().next().getName());

                return message;
            }

            @Override
            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
                window.println("Proposal to " + cfp.getSender().getName() + " was accepted !");
                return super.handleAcceptProposal(cfp, propose, accept);
            }
        });

//        addBehaviour(new AchieveREResponder(this, model) {
//
//            @Override
//            public void onStart() {
//                println("Start of AchieveREResponder");
//                println("This is my AID : " + myAgent.getAID());
//                super.onStart();
//            }
//
//            @Override
//            public int onEnd() {
//                println("End of AchieveREResponder");
//                return super.onEnd();
//            }
//
//            @Override
//            protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
//                println("Reçu CFP : " + request.getContent());
//                Random random = new Random();
//                int value = random.nextInt(100);
//                println("Value : " + value);
//                ACLMessage answer = request.createReply();
//                answer.setPerformative(ACLMessage.AGREE);
//                answer.setContent(String.valueOf(value));
//                return answer;
//            }
//
//            @Override
//            protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
//                ACLMessage answer = request.createReply();
//                answer.setPerformative(ACLMessage.INFORM);
//                answer.setContent("Ceci est le contenu du message inform !");
//                println("J'envoie le message INFORM");
//                return answer;
//            }
//        });

    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }
}
