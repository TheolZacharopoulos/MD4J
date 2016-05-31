package nl.cwi.examples.runtime_state_machine;

import nl.cwi.examples.runtime_state_machine.schemas.RMachine;
import nl.cwi.examples.runtime_state_machine.schemas.RState;
import nl.cwi.examples.state_machine.RunStateMachine;
import nl.cwi.examples.state_machine.StateMachineFactory;
import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.data_manager.IDataManager;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

import java.util.Arrays;

import static nl.cwi.examples.state_machine.Doors.*;

public class RuntimeStateMachineExample extends RunStateMachine {

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
            System.out.println(state.name() + " views: " + ((RState)state).visits());
        }
    }

}
