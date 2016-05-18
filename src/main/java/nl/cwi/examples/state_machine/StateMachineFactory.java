package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.language.IFactory;

public interface StateMachineFactory extends IFactory {
    State State();
    State State(String name);
    Machine Machine();
    Transition Transition();
    Transition Transition(String event);
}
