package nl.cwi.examples.runtime_state_machine;

import nl.cwi.examples.runtime_state_machine.schemas.RMachine;
import nl.cwi.examples.runtime_state_machine.schemas.RState;
import nl.cwi.examples.state_machine.StateMachineFactory;
import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.java_managed_data.framework.SchemaFactoryProvider;
import nl.cwi.java_managed_data.language.data_manager.BasicDataManager;
import nl.cwi.java_managed_data.language.data_manager.IDataManager;
import nl.cwi.java_managed_data.language.schema.boot.SchemaFactory;
import nl.cwi.java_managed_data.language.schema.load.SchemaLoader;
import nl.cwi.java_managed_data.language.schema.models.definition.Schema;

import java.util.Arrays;

public class RuntimeStateMachineExample {

    public final static String OPEN_STATE   = "Open";
    public final static String CLOSED_STATE = "Closed";
    public final static String LOCKED_STATE = "Locked";

    public final static String OPEN_EVENT   = "open_door";
    public final static String CLOSE_EVENT  = "close_door";
    public final static String LOCK_EVENT   = "lock_door";
    public final static String UNLOCK_EVENT = "unlock_door";

    public static void main(String[] args) {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema =
                SchemaLoader.load(
                    schemaFactory,
                    Machine.class, State.class, Transition.class, RMachine.class, RState.class);

        final IDataManager dataManager = new BasicDataManager();
        final StateMachineFactory stateMachineFactory =
                dataManager.factory(RuntimeStateMachineFactory.class, stateMachineSchema);

        final RMachine doorStateMachine = (RMachine) doors(stateMachineFactory);

        Arrays.asList(LOCK_EVENT, UNLOCK_EVENT, OPEN_EVENT).forEach(doorStateMachine::execute);

        for (State state : doorStateMachine.states()) {
            System.out.println(state.name() + " views: " + ((RState)state).views());
        }
    }

    private static Machine doors(StateMachineFactory stateMachineFactory) {
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
