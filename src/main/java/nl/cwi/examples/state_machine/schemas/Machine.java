package nl.cwi.examples.state_machine.schemas;

import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

import java.util.Set;

public interface Machine extends M {
    State start(State... startingState);

    State current(State... currentState);

    @Contain
    Set<State> states(State... states);
}
