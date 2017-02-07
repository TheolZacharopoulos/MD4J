package nl.cwi.examples.ccconcerns.patterns.observer.lockable_observable;

import nl.cwi.examples.ccconcerns.patterns.lockable.Lockable;
import nl.cwi.examples.ccconcerns.patterns.lockable.LockableMObject;
import nl.cwi.examples.ccconcerns.patterns.observer.Observable;
import nl.cwi.examples.ccconcerns.patterns.observer.ObservableMObject;
import nl.cwi.examples.ccconcerns.patterns.observer.Observer;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;

public class LockableObservableMObject extends MObject implements Lockable, Observable {

    private LockableMObject lockableMObject;
    private ObservableMObject observableMObject;

    public LockableObservableMObject(Klass schemaKlass, Object... initializers) {
        super(schemaKlass, initializers);
        lockableMObject = new LockableMObject(schemaKlass, initializers);
        observableMObject = new ObservableMObject(schemaKlass, initializers);
    }

    @Override
    public void addObserver(Observer _observer) {
        observableMObject.addObserver(_observer);
    }

    @Override
    public void lock() {
        lockableMObject.lock();
    }

    @Override
    public void _set(String name, Object value) throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
        lockableMObject._set(name, value);
        observableMObject._set(name, value);
        super._set(name, value);
    }
}