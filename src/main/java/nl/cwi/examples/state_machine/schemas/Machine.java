package nl.cwi.examples.state_machine.schemas;

import nl.cwi.java_managed_data.M;

import java.util.Set;

public interface Machine extends M {
    State start(State... startingState);

    State current(State... currentState);

    Set<State> states(State... states);
}
