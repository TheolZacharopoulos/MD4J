package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;

public final class SimpleDoors {
	public static Machine doors(StateMachineFactory stateMachineFactory) {
        // ========================================================
        // Door State Machine definition
        final Machine doorStateMachine = stateMachineFactory.Machine();

        // Open State definition
        final State openState = stateMachineFactory.State();
        openState.name("Opened");
        openState.machine(doorStateMachine);

        // Closed State definition
        final State closedState = stateMachineFactory.State();
        closedState.name("Closed");
        closedState.machine(doorStateMachine);

        
        // Close Transition
        final Transition closeTransition = stateMachineFactory.Transition();
        closeTransition.event("close");
        closeTransition.from(openState);
        closeTransition.to(closedState);

        // Open Transition
        final Transition openTransition = stateMachineFactory.Transition();
        openTransition.event("open");
        openTransition.from(closedState);
        openTransition.to(openState);

        // State machine start
        doorStateMachine.start(closedState);

        return doorStateMachine;
    }
}
