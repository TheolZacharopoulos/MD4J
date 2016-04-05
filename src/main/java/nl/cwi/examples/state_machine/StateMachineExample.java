package nl.cwi.examples.state_machine;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class StateMachineExample {

    public static void main(String[] args) {

        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema = SchemaLoader.load(schemaFactory, schemaSchema, State.class, Transition.class, Machine.class);
        final BasicFactory basicFactoryForStateMachines = new BasicFactory(StateMachineFactory.class, stateMachineSchema);
        final StateMachineFactory stateMachineFactory = basicFactoryForStateMachines.make();

        // TODO: 
    }
}
