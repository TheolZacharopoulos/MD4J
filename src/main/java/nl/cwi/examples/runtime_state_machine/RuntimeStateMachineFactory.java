package nl.cwi.examples.runtime_state_machine;

import nl.cwi.examples.runtime_state_machine.schemas.RMachine;
import nl.cwi.examples.runtime_state_machine.schemas.RState;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.language.IFactory;

public interface RuntimeStateMachineFactory extends IFactory {
    RState State();
    RMachine Machine();
    Transition Transition();
}
