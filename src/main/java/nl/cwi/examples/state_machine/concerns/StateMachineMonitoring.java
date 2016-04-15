package nl.cwi.examples.state_machine.concerns;

import nl.cwi.examples.state_machine.StateMachineExampleMonitoring;
import nl.cwi.examples.state_machine.schemas.State;

public class StateMachineMonitoring {
    public static void monitor(Object obj, String field, Object value) {
        if (field.equals("current")) {
            System.out.println(" > Current state changed to " + ((State) value).name());
        }
    }

    public static void notify(Object obj, String field, Object value) {
        if (field.equals("current") && ((State) value).name().equals(StateMachineExampleMonitoring.CLOSED_STATE)) {
            final String message = "Someone just closed the door!";
            System.err.println(message);
        }
    }
}
