package nl.cwi.examples.state_machine.concerns;

import nl.cwi.examples.state_machine.StateMachineExampleMonitoring;
import nl.cwi.examples.state_machine.schemas.State;

public class StateMachineMonitoring {
    public static void monitor(Object obj, String fieldName, Object newState) {
        if (fieldName.equals("current")) {
            System.out.println(" > Current state changed to " + ((State) newState).name());
        }
    }

    public static void notify(Object obj, String field, Object newState) {
        if (field.equals("current") && ((State) newState).name().equals(StateMachineExampleMonitoring.OPEN_STATE)) {
            System.err.println("Someone just opened the door!");
        }
    }
}
