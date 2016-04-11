package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.ccconcerns.aspects.UpdateLogger;
import nl.cwi.managed_data_4j.ccconcerns.patterns.observer.Observable;
import nl.cwi.managed_data_4j.ccconcerns.patterns.observer.ObservableFactory;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StateMachineExample {

    public final static String OPEN_STATE       = "Open";
    public final static String CLOSED_STATE     = "Closed";
    public final static String OPEN_TRANSITION  = "open";
    public final static String CLOSE_TRANSITION = "close";

    public static void main(String[] args) {

        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema =
                SchemaLoader.load(schemaFactory, schemaSchema, State.class, Transition.class, Machine.class);
        final BasicFactory basicFactoryForStateMachines = new BasicFactory(StateMachineFactory.class, stateMachineSchema);
        final StateMachineFactory stateMachineFactory = basicFactoryForStateMachines.make();

//        // ================================ Update Logging ===================================
//        System.out.println("=============");
//        System.out.println("Observable Objects: ");
//
//        final ObservableFactory observableFactory = new ObservableFactory(StateMachineFactory.class, stateMachineSchema);
//        final StateMachineFactory observableStateMachineFactory = observableFactory.make();
//
//        final State openState = observableStateMachineFactory.State();
//
//        // Add Logging concerns
//        ((Observable) openState).observe(UpdateLogger::log);
//        // ===================================================================================

        final State openState = stateMachineFactory.State();

        openState.name(OPEN_STATE);

        final State closedState = stateMachineFactory.State();
        closedState.name(CLOSED_STATE);

        final Transition closeTransition = stateMachineFactory.Transition();
        closeTransition.event(CLOSE_TRANSITION);
        closeTransition.from(openState);
        closeTransition.to(closedState);

        final Transition openTransition = stateMachineFactory.Transition();
        openTransition.event(OPEN_TRANSITION);
        openTransition.from(closedState);
        openTransition.to(openState);

        openState.in(openTransition);
        openState.out(closeTransition);

        closedState.in(closeTransition);
        closedState.out(openTransition);

        final Machine doorStateMachine = stateMachineFactory.Machine();
        doorStateMachine.start(openState);
        doorStateMachine.states(openState, closedState);
        openState.machine(doorStateMachine);
        closedState.machine(doorStateMachine);

        final List<String> commands = new LinkedList<>(Arrays.asList(
                OPEN_TRANSITION,
                CLOSE_TRANSITION,
                OPEN_TRANSITION,
                CLOSE_TRANSITION,
                OPEN_TRANSITION));

        interpretStateMachine(doorStateMachine, commands);
    }

    private static void interpretStateMachine(Machine sm, List<String> commands) {

        State current = sm.start();
        System.out.println("Start: " + current.name());

        for (String event : commands) {
            Transition transition = current.out().stream()
                .filter(trans -> trans.event().equals(event))
                .findFirst()
                .orElse(null);

            if (transition != null) {
                current = transition.to();
                System.out.println(
                    "Changed: " + transition.from().name() + " to " + transition.to().name() + ", current: " + current.name());
            }
        }
    }
}
