package nl.cwi.examples.state_machine.data_managers;

@FunctionalInterface
public interface StateChangeManager {
    void addStateChangeAction(StateChangeAction action);
}
