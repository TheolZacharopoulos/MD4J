package nl.cwi.examples.state_machine.schemas;

import nl.cwi.managed_data_4j.M;

import java.util.Set;

public interface Machine extends M {
    State start(State... startingState);

    State current(State... currentState);

    Set<State> states(State... states);
}
