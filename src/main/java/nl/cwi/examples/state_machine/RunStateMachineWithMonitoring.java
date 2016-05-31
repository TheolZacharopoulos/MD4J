package nl.cwi.examples.state_machine;

import static nl.cwi.examples.state_machine.Doors.LOCK_EVENT;
import static nl.cwi.examples.state_machine.Doors.OPEN_EVENT;
import static nl.cwi.examples.state_machine.Doors.UNLOCK_EVENT;
import static nl.cwi.examples.state_machine.Doors.doors;

import java.util.Arrays;
import java.util.LinkedList;

import nl.cwi.examples.ccconcerns.patterns.observer.Observable;
import nl.cwi.examples.ccconcerns.patterns.observer.ObservableDataManager;
import nl.cwi.examples.state_machine.ccc.StateMachineLogging;
import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.IDataManager;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class RunStateMachineWithMonitoring extends RunStateMachine {

    public static void main(String[] args) {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        // ========================================================
        // State Machine monitoring
        final Schema stateMachineSchema =
                SchemaLoader.load(schemaFactory, Machine.class, State.class, Transition.class);

        final IDataManager dataManager = new ObservableDataManager();
        final StateMachineFactory stateMachineFactory =
                dataManager.factory(StateMachineFactory.class, stateMachineSchema);

        final Machine doorStateMachine = doors(stateMachineFactory);

        // Add logging concern
        ((Observable) doorStateMachine)
                .observe(StateMachineLogging::logCurrentStateChanges);

        interpretStateMachine(doorStateMachine, new LinkedList<>(Arrays.asList(
                LOCK_EVENT,
                UNLOCK_EVENT,
                OPEN_EVENT)));
    }

}
