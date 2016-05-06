package nl.cwi.examples.state_machine.data_managers;

import nl.cwi.examples.state_machine.schemas.State;

@FunctionalInterface
public interface StateChangeAction {
    void stateChanged(State newState);
}
