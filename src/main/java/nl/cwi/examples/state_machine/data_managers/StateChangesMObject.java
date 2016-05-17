package nl.cwi.examples.state_machine.data_managers;

import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;

import java.util.LinkedList;
import java.util.List;

public class StateChangesMObject extends MObject implements StateChangeManager {

    private List<StateChangeAction> stateChangeActions;

    public StateChangesMObject(Klass schemaKlass, Object... initializers) {
        super(schemaKlass, initializers);
        stateChangeActions = new LinkedList<>();
    }

    @Override
    public void _set(String name, Object value) throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
        if ("current".equals(name)) {
            executeStateChangeActions((State) value);
        }
        super._set(name, value);
    }

    protected void executeStateChangeActions(State newState) {
        stateChangeActions.forEach(stateChange -> stateChange.stateChanged(newState));
    }

    @Override
    public void addStateChangeAction(StateChangeAction action) {
        stateChangeActions.add(action);
    }
}