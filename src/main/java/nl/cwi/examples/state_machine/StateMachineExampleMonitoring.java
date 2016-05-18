package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.data_managers.StateChangeManager;
import nl.cwi.examples.state_machine.data_managers.StateChangesDataManager;
import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StateMachineExampleMonitoring {

    public final static String OPEN_STATE       = "Open";
    public final static String CLOSED_STATE     = "Closed";
    public final static String LOCKED_STATE     = "Locked";

    public final static String OPEN_TRANSITION  = "open_door";
    public final static String CLOSE_TRANSITION = "close_door";
    public final static String LOCK_TRANSITION  = "lock_door";
    public final static String UNLOCK_TRANSITION = "unlock_door";

    public static void main(String[] args) {
        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema =
                SchemaLoader.load(schemaFactory, schemaSchema, Machine.class, State.class, Transition.class);
        final BasicDataManager basicDataManagerForStateMachines = new BasicDataManager();
        final StateMachineFactory stateMachineFactory = basicDataManagerForStateMachines.factory(StateMachineFactory.class, stateMachineSchema);

        // ========================================================
        // State Machine monitoring
        final StateChangesDataManager stateChangesDataManager =
                new StateChangesDataManager();
        final StateMachineFactory stateChangesMachineFactory = stateChangesDataManager.factory(StateMachineFactory.class, stateMachineSchema);

        // ========================================================
        // Door State Machine definition, with state changes data manager
        final Machine doorStateMachine = stateChangesMachineFactory.Machine();

        // Add Monitoring concerns
        ((StateChangeManager) doorStateMachine)
                .addStateChangeAction(
                    (newState) -> System.out.println(" > State changed to " + newState.name()),
                    (name, value) -> "current".equals(name));

        ((StateChangeManager) doorStateMachine)
                .addStateChangeAction(
                    (newState) -> System.err.println("Someone just opened the door!"),
                    (name, newState) -> "current".equals(name) &&
                        newState.name().equals(StateMachineExampleMonitoring.OPEN_STATE));

        // Open State definition
        final State openState = stateMachineFactory.State(OPEN_STATE);
        openState.machine(doorStateMachine);

        // Closed State definition
        final State closedState = stateMachineFactory.State(CLOSED_STATE);
        closedState.machine(doorStateMachine);

        // Locked State definition
        final State lockedState = stateMachineFactory.State(LOCKED_STATE);
        lockedState.machine(doorStateMachine);

        // Close Transition
        final Transition closeTransition = stateMachineFactory.Transition(CLOSE_TRANSITION);
        closeTransition.from(openState);
        closeTransition.to(closedState);

        // Open Transition
        final Transition openTransition = stateMachineFactory.Transition(OPEN_TRANSITION);
        openTransition.from(closedState);
        openTransition.to(openState);

        // Lock Transition
        final Transition lockTransition = stateMachineFactory.Transition(LOCK_TRANSITION);
        lockTransition.from(closedState);
        lockTransition.to(lockedState);

        // Unlock Transition
        final Transition unlockTransition = stateMachineFactory.Transition(UNLOCK_TRANSITION);
        unlockTransition.from(lockedState);
        unlockTransition.to(closedState);

        // State machine start
        doorStateMachine.start(closedState);

        interpretStateMachine(doorStateMachine, new LinkedList<>(Arrays.asList(
                LOCK_TRANSITION,
                UNLOCK_TRANSITION,
                OPEN_TRANSITION)));
    }

    private static void interpretStateMachine(Machine stateMachine, List<String> commands) {

        stateMachine.current(stateMachine.start());

        for (String event : commands) {
            for (Transition trans : stateMachine.current().out()) {
                if (trans.event().equals(event)) {
                    stateMachine.current(trans.to());
                    break;
                }
            }
        }
    }
}
