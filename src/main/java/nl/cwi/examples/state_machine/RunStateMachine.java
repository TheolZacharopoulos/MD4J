package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.*;

import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.data_manager.IDataManager;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static nl.cwi.examples.state_machine.Doors.*;

public class RunStateMachine {

    public static void main(String[] args) {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema =
                SchemaLoader.load(schemaFactory, Machine.class, State.class, Transition.class);
        final IDataManager dataManager = new BasicDataManager();
        final StateMachineFactory stateMachineFactory =
                dataManager.factory(StateMachineFactory.class, stateMachineSchema);

        final Machine doorStateMachine = doors(stateMachineFactory);

        interpretStateMachine(doorStateMachine, new LinkedList<>(Arrays.asList(
                Doors.LOCK_EVENT,
                Doors.UNLOCK_EVENT,
                Doors.OPEN_EVENT)));
    }

    public static void interpretStateMachine(Machine stateMachine, List<String> commands) {
        State current = stateMachine.start();

        for (String event : commands) {
            for (Transition trans : current.out()) {
                if (trans.event().equals(event)) {
                	System.out.println("event = " + event);
                    current = trans.to();
                    break;
                }
            }
        }
    }
}
