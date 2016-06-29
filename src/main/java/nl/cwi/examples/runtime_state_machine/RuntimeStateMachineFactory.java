package nl.cwi.examples.runtime_state_machine;

import nl.cwi.examples.runtime_state_machine.schemas.RMachine;
import nl.cwi.examples.runtime_state_machine.schemas.RState;
import nl.cwi.examples.state_machine.StateMachineFactory;

public interface RuntimeStateMachineFactory extends StateMachineFactory {
    @Override
	RState State();
    
    @Override
	RMachine Machine();
}
