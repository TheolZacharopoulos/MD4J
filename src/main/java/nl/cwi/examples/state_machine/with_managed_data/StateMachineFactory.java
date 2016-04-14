package nl.cwi.examples.state_machine.with_managed_data;

import nl.cwi.examples.state_machine.with_managed_data.schemas.Machine;
import nl.cwi.examples.state_machine.with_managed_data.schemas.State;
import nl.cwi.examples.state_machine.with_managed_data.schemas.Transition;

public interface StateMachineFactory {
    State State();
    State State(String name);
    Machine Machine();
    Transition Transition();
    Transition Transition(String event);
}
