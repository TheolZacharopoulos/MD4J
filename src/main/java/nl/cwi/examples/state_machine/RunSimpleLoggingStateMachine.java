package nl.cwi.examples.state_machine;

import nl.cwi.examples.ccconcerns.patterns.simplelogging.LoggingDataManager;
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
import java.util.stream.Collectors;

import static nl.cwi.examples.state_machine.SimpleDoors.*;

public class RunSimpleLoggingStateMachine {

    public static void main(String[] args) {
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        final Schema stateMachineSchema =
                SchemaLoader.load(schemaFactory, Machine.class, State.class, Transition.class);
        final IDataManager dataManager = new LoggingDataManager("event", "name");
        final StateMachineFactory stateMachineFactory =
                dataManager.factory(StateMachineFactory.class, stateMachineSchema);

        final Machine doorStateMachine = doors(stateMachineFactory);
        
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
    
    static void printActivity(Machine m) {
        System.out.println("start state: " + m.start().name());
        System.out.println("states: " + 
          m.states().stream()
            .map(s -> s.name())
            .collect(Collectors.toSet()));
        System.out.println("transitions: " + 
          m.states().stream()
            .flatMap(s -> s.out().stream().map(t -> t.event()))
            .collect(Collectors.toSet()));
    }
}
