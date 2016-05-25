package nl.cwi.examples.state_machine.schemas;

import nl.cwi.java_managed_data.M;
import nl.cwi.java_managed_data.language.schema.models.definition.annotations.Inverse;
import nl.cwi.java_managed_data.language.schema.models.definition.annotations.Key;

import java.util.List;

public interface State extends M {

    @Key
    String name(String... name);

    @Inverse(other = Machine.class, field = "states")
    Machine machine(Machine... machine);

    List<Transition> out(Transition... transition);

    @Inverse(other = State.class, field = "out")
    List<Transition> in(Transition... transition);
}
