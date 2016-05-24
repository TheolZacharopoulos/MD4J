package nl.cwi.examples.state_machine.ccc;

import nl.cwi.examples.state_machine.schemas.State;

public class StateMachineLogging {
    public static void logCurrentStateChanges(Object thisObj, String fieldName, Object newValue) {
        if (fieldName.equals("current")) {
            System.out.println(" > State changed to " + ((State)newValue).name());
        }
    }
}
