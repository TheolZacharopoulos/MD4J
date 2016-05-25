package nl.cwi.examples.state_machine.data_managers;

import nl.cwi.examples.state_machine.schemas.State;
import nl.cwi.java_managed_data.language.managed_object.MObject;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.java_managed_data.language.schema.models.definition.Klass;

import java.util.LinkedList;
import java.util.List;

public class StateChangesMObject extends MObject implements StateChangeManager {

    private class Tuple<A, P> {
        private A action;
        private P predicate;

        public Tuple(A action, P predicate) {
            this.action = action;
            this.predicate= predicate;
        }

        public A getAction() {
            return action;
        }

        public P getPredicate() {
            return predicate;
        }
    }

    private List<Tuple<StateChangeAction, FieldPredicate>> stateChangeActions;

    public StateChangesMObject(Klass schemaKlass, Object... initializers) {
        super(schemaKlass, initializers);
        stateChangeActions = new LinkedList<>();
    }

    @Override
    public void _set(String name, Object value) throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
        executeStateChangeActions((State) value, name);
        super._set(name, value);
    }

    protected void executeStateChangeActions(State newState, String fieldName) {
        for (Tuple<StateChangeAction, FieldPredicate> stateChange : stateChangeActions) {
            if (stateChange.getPredicate().fieldChanged(fieldName, newState)) {
                stateChange.getAction().stateChanged(newState);
            }
        }
    }

    @Override
    public void addStateChangeAction(StateChangeAction action, FieldPredicate predicate) {
        stateChangeActions.add(new Tuple<>(action, predicate));
    }
}