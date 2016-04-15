package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;

public interface StateMachineFactory {
    State State();
    State State(String name);
    Machine Machine();
    Transition Transition();
    Transition Transition(String event);
}
