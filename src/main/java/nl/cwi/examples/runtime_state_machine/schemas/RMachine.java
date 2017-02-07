package nl.cwi.examples.runtime_state_machine.schemas;

import nl.cwi.examples.state_machine.schemas.Machine;
import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.examples.state_machine.schemas.Transition;

public interface RMachine extends Machine {

	State current(State... current);

	default void execute(String event) {
		for (Transition t : current().out())
			if (t.event().equals(event)) {
				RState rc = (RState) current();
				rc.count(rc.count() + 1);
				current(t.to());
				System.out.println(current().name());
				return;
			}
	}

}
