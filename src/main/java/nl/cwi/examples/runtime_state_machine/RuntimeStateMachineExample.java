package nl.cwi.examples.runtime_state_machine;

import static nl.cwi.examples.state_machine.SimpleDoors.doors;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import nl.cwi.examples.ccconcerns.patterns.observer.Observable;
import nl.cwi.examples.ccconcerns.patterns.observer.ObservableDataManager;
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

public class RuntimeStateMachineExample extends RunStateMachine {

    public static void main(String[] args) {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema =
                SchemaLoader.load(
                    schemaFactory,
                    Transition.class, RMachine.class, RState.class);

        final IDataManager dataManager = new BasicDataManager();
        final StateMachineFactory stateMachineFactory =
                dataManager.factory(RuntimeStateMachineFactory.class, stateMachineSchema);
        
        

        final RMachine doorStateMachine = (RMachine) doors(stateMachineFactory);

//        ((Observable) doorStateMachine).addObserver(
//        		(obj, name, state) -> {
//        		  if (name.equals("current")) {
//        		    System.out.println(" > State changed to " 
//        		      + ((State)state).name());
//        		  }
//        		});
        
        doorStateMachine.current(doorStateMachine.start());
        //Arrays.asList(LOCK_EVENT, UNLOCK_EVENT, OPEN_EVENT).forEach(doorStateMachine::execute);
        Arrays.asList("open", "close").forEach(doorStateMachine::execute);
        		
        
        for (State state : doorStateMachine.states()) {
            System.out.println(state.name() + " views: " + ((RState)state).count());
        }
        
        System.out.println(countStates(doorStateMachine));
        
//        interpretStateMachine(doorStateMachine, new LinkedList<>(Arrays.asList(
//                LOCK_EVENT,
//                UNLOCK_EVENT,
//                OPEN_EVENT)));
        
        System.out.println(countStates(doorStateMachine));
    }  
        
    public static Set<String> countStates(Machine stateMachine) {
    	 return stateMachine.states()
    	    .stream().map(s -> { return s.name(); })
    	    .collect(Collectors.toSet());
    	}

}
