package behaviours;

import agents.UserAgent;
import jade.core.behaviours.OneShotBehaviour;
import models.Product;

public class DoIHaveKnowledgeBehaviour extends OneShotBehaviour {

    int agentSkill;
    Product brokenProduct;

    public DoIHaveKnowledgeBehaviour(UserAgent a) {
        super(a);
        agentSkill = a.getSkill();
        brokenProduct = a.getBrokenProduct();
    }

    @Override
    public int onEnd() {
        // returns 1 if user has knowledge, 0 otherwise.
        if (agentSkill == 0) {
            return 0;
        } else {
            return agentSkill >= brokenProduct.getBreakdownSeverity() ? 1 : 0;
        }
    }
}
