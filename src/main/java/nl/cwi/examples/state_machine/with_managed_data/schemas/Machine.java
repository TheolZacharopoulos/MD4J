package nl.cwi.examples.state_machine.with_managed_data.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

import java.util.Set;

public interface Machine {
    State start(State... state);

    @Contain
    Set<State> states(State... state);
}
