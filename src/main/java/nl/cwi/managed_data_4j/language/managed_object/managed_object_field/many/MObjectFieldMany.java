package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.many;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.MObjectField;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.utils.PrimitiveUtils;

/**
 * Represents a multi value field.
 * @author Theologos Zacharopoulos
 */
public abstract class MObjectFieldMany<T> extends MObjectField<T> implements Iterable {

    public MObjectFieldMany(MObject owner, Field field) throws UnknownTypeException {
        super(owner, field);
        this.value = null;
    }

    @Override
    public void init(T values) throws InvalidFieldValueException {
        if (!PrimitiveUtils.isMany(values.getClass())) {
            throw new InvalidFieldValueException("Non-array value passed to many-field");
        }
    }

    @Override
    public void set(T value) throws InvalidFieldValueException {
        throw new InvalidFieldValueException("Cannot assign to many-values field " + field.name());
    }

    public void check(T value) throws InvalidFieldValueException {}

    protected T defaultValue() throws UnknownTypeException {
        return null;
    }

    public abstract void __insert(M object);
    public abstract void __delete(M object);
}
