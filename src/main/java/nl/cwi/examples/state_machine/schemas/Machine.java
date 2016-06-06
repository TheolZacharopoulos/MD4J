package nl.cwi.examples.state_machine.schemas;

import java.util.Set;

import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

public interface Machine extends M {
    State start(State... startingState);

    @Contain
    Set<State> states(State... states);
}
