package behaviors;

import jade.core.behaviours.Behaviour;
import static java.lang.System.out;

public class CountdownBehaviour extends Behaviour {

    int timeLeft = 10;

    @Override
    public void action() {
        block(1000);
        timeLeft--;
        out.println("[" + myAgent.getLocalName() + "] Time left : " + timeLeft + "s");
    }

    @Override
    public boolean done() {
        boolean end = (timeLeft <= 0);
        if (end) {myAgent.doDelete();}
        return end;
    }
}
