package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class StateMachineExample {

    public static void main(String[] args) {

        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema = SchemaLoader.load(schemaFactory, schemaSchema, State.class, Transition.class, Machine.class);
        final BasicFactory basicFactoryForStateMachines = new BasicFactory(StateMachineFactory.class, stateMachineSchema);
        final StateMachineFactory stateMachineFactory = basicFactoryForStateMachines.make();

        State open = stateMachineFactory.State();
        open.name("Open");

        State closed = stateMachineFactory.State();
        closed.name("Closed");

        Transition closeTransition = stateMachineFactory.Transition();
        closeTransition.event("close");
        closeTransition.from(open, closed);

        Transition openTransition = stateMachineFactory.Transition();
        openTransition.event("open");
        openTransition.from(closed, open);

        Machine doorStateMachine = stateMachineFactory.Machine();
        doorStateMachine.start(open);
        doorStateMachine.states(new HashSet<>(Arrays.asList(open, closed)).toArray(new State[2]));

//        interpretStateMachine(doorStateMachine);
    }

    private static void interpretStateMachine(Machine sm, List<String> commands) {
        State current = sm.start();

        System.out.println(current.name());

        for (String command : commands) {

        }
    }
}
