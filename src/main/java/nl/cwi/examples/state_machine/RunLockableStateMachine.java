package nl.cwi.examples.state_machine;

import nl.cwi.examples.ccconcerns.patterns.lockable.Lockable;
import nl.cwi.examples.ccconcerns.patterns.lockable.LockableDataManager;
import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.IDataManager;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class RunLockableStateMachine {

	public static void main(String[] args) {
		final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

		final Schema stateMachineSchema = SchemaLoader.load(schemaFactory, Machine.class, State.class,
				Transition.class);
		IDataManager dataManager = new LockableDataManager();
		StateMachineFactory factory = dataManager.factory(StateMachineFactory.class, stateMachineSchema);
		Machine m = factory.Machine();
		State s1 = factory.State();
		m.start(s1);
		((Lockable) m).lock();
		State s2 = factory.State();
		m.start(s2);

	}

}
