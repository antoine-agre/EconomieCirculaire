package behaviors;

import jade.core.behaviours.FSMBehaviour;

public class BreakdownFSMBehaviour extends FSMBehaviour {

    @Override
    public void onStart() {
        super.onStart();
        registerFirstState(new CountdownBehaviour(), "test");
    }
}
