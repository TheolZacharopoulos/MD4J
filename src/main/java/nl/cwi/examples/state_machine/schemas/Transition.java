package nl.cwi.examples.state_machine.schemas;

import nl.cwi.java_managed_data.M;
import nl.cwi.java_managed_data.language.schema.models.definition.annotations.Inverse;

public interface Transition extends M {

    String event(String... event);

    @Inverse(other = State.class, field = "out")
    State from(State... from);

    @Inverse(other = State.class, field = "in")
    State to(State... to);
}
