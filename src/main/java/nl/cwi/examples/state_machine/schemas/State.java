package nl.cwi.examples.state_machine.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Key;

import java.util.Set;

public interface State extends M {

    @Inverse(other = Machine.class, field = "states")
    Machine machine(Machine... machine);

    @Key
    String name(String... name);

    @Contain
    Set<Transition> out(Transition... transition);

    Set<Transition> in(Transition... transition);
}
