package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;

public final class Doors {
	 public final static String OPEN_STATE       = "Open";
	 public final static String CLOSED_STATE     = "Closed";
	 public final static String LOCKED_STATE     = "Locked";

	 public final static String OPEN_EVENT   = "open_door";
	 public final static String CLOSE_EVENT  = "close_door";
	 public final static String LOCK_EVENT   = "lock_door";
	 public final static String UNLOCK_EVENT = "unlock_door";

	public static Machine doors(StateMachineFactory stateMachineFactory) {
        // ========================================================
        // Door State Machine definition
        final Machine doorStateMachine = stateMachineFactory.Machine();

        // Open State definition
        final State openState = stateMachineFactory.State();
        openState.name(OPEN_STATE);
        openState.machine(doorStateMachine);

        // Closed State definition
        final State closedState = stateMachineFactory.State();
        closedState.name(CLOSED_STATE);
        closedState.machine(doorStateMachine);

        // Locked State definition
        final State lockedState = stateMachineFactory.State();
        lockedState.name(LOCKED_STATE);
        lockedState.machine(doorStateMachine);

        // Close Transition
        final Transition closeTransition = stateMachineFactory.Transition();
        closeTransition.event(CLOSE_EVENT);
        closeTransition.from(openState);
        closeTransition.to(closedState);

        // Open Transition
        final Transition openTransition = stateMachineFactory.Transition();
        openTransition.event(OPEN_EVENT);
        openTransition.from(closedState);
        openTransition.to(openState);

        // Lock Transition
        final Transition lockTransition = stateMachineFactory.Transition();
        lockTransition.event(LOCK_EVENT);
        lockTransition.from(closedState);
        lockTransition.to(lockedState);

        // Unlock Transition
        final Transition unlockTransition = stateMachineFactory.Transition();
        unlockTransition.event(UNLOCK_EVENT);
        unlockTransition.from(lockedState);
        unlockTransition.to(closedState);

        // State machine start
        doorStateMachine.start(closedState);

        return doorStateMachine;
    }
}
