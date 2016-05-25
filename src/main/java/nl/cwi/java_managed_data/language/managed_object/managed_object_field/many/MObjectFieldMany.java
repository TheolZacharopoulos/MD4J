package nl.cwi.java_managed_data.language.managed_object.managed_object_field.many;

import nl.cwi.java_managed_data.language.managed_object.MObject;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.MObjectField;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.java_managed_data.language.schema.models.definition.Field;
import nl.cwi.java_managed_data.language.primitives.PrimitivesManager;

/**
 * Represents a multi value field.
 * @author Theologos Zacharopoulos
 */
public abstract class MObjectFieldMany extends MObjectField implements Iterable {

    public MObjectFieldMany(MObject owner, Field field) throws UnknownTypeException {
        super(owner, field);
    }

    @Override
    public void init(Object values) throws InvalidFieldValueException, NoKeyFieldException {
        if (!PrimitivesManager.getInstance().isMany(values.getClass())) {
            throw new InvalidFieldValueException("Non-array value passed to many-field");
        }
    }

    @Override
    public void set(Object value) throws InvalidFieldValueException {
        throw new InvalidFieldValueException("Cannot assign to many-values field " + field.name());
    }

    public void check(Object value) throws InvalidFieldValueException {}

    protected Object defaultValue() throws UnknownTypeException {
        return null;
    }

    public abstract void add(Object value) throws NoKeyFieldException;

    public abstract void __insert(Object value) throws NoKeyFieldException;

    public abstract void __delete(Object value) throws NoKeyFieldException;

    public abstract boolean isEmpty();

    public abstract void clear();
}