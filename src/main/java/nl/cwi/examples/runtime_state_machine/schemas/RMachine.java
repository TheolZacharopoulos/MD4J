package nl.cwi.examples.runtime_state_machine.schemas;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;

public interface RMachine extends Machine {

	State current(State... currentState);


    default void execute(String event) {
        for (State state : states()) {
            if (((RState)state).handle(event)) {
                current(state);
                return;
            }
        }
    }
}
