package nl.cwi.examples.state_machine.concerns;

import nl.cwi.examples.state_machine.StateMachineExampleMonitoring;
import nl.cwi.examples.state_machine.schemas.State;

public class StateMachineMonitoringConcerns {

    public static void monitor(State newState) {
        System.out.println(" > State changed to " + newState.name());
    }

    public static void notify(State newState) {
        if (newState.name().equals(StateMachineExampleMonitoring.OPEN_STATE)) {
            System.err.println("Someone just opened the door!");
        }
    }
}
