package nl.cwi.examples.runtime_state_machine.schemas;

import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;

public interface RState extends State {
    Integer count(Integer... count);

    default boolean handle(String event) {
        for (Transition transition : out()) {
            if (transition.event().equals(event)) {
                count(count() + 1);
                return true;
            }
        }
        return false;
    }
}
