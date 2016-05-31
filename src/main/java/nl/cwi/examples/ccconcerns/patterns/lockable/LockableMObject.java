package nl.cwi.examples.ccconcerns.patterns.lockable;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;

public class LockableMObject extends MObject implements Lockable {

    private boolean isLocked = false;

    public LockableMObject(Klass schemaKlass, Object... initializers) {
        super(schemaKlass, initializers);
    }

    public void lock() {
        isLocked = true;
    }

    /**
     * Simple decorator that locks the object
     * @param name the name of the field
     * @param value the value of the field
     * @throws NoSuchFieldError if no field found.
     * @throws InvalidFieldValueException if no valid value is set to the field.
     * @throws NoKeyFieldException if no key field found.
     */
    @Override
    public void _set(String name, Object value) throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
        if (isLocked) {
            throw new IllegalStateException("Cannot change " + name + " of locked object " + schemaKlass.name() + ".");
        }
        super._set(name, value);
    }
}