package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.java_managed_data.IFactory;

public interface StateMachineFactory extends IFactory {
    State State();
    Machine Machine();
    Transition Transition();
}
