package nl.cwi.examples.ccconcerns.patterns.observer;

import java.util.ArrayList;
import java.util.List;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;

/**
 * A Managed Object that can be observed
 * @author Theologos Zacharopoulos
 */
public class ObservableMObject extends MObject implements Observable {

    // a list of observers for that object
    private List<Observe> observers;

    public ObservableMObject(Klass schemaKlass, Object... initializers) {
        super(schemaKlass, initializers);
        observers = new ArrayList<Observe>();
    }

    /**
     * Public method that adds new observes to the object
     * @param _observer the observe function to be added
     */
    public void observe(Observe _observer) {
        observers.add(_observer);
    }

    @Override
    public void _set(String _name, Object _value) throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
        super._set(_name, _value);

        // Run the observe function for each of the observers on every "set"
        observers.forEach(observer -> observer.observe(thisObject, _name, _value));
    }
}