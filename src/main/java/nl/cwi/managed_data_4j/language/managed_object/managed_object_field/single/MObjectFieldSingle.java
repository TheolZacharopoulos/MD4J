package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.single;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.MObjectField;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;

/**
 * Represents a single value field
 * @author Theologos Zacharopoulos
 */
public abstract class MObjectFieldSingle extends MObjectField {

    protected Object value;

    public MObjectFieldSingle(MObject owner, Field field)
            throws UnknownTypeException, InvalidFieldValueException
    {
        super(owner, field);
        this.value = defaultValue();
    }

    public void init(Object value) throws InvalidFieldValueException, NoKeyFieldException {
        this.set(value);
    }

    @Override
    public void set(Object value) throws InvalidFieldValueException, NoKeyFieldException {
        if (value != null) {
            this.check(value);
        }
        this.value = value;
    }

    @Override
    protected Object defaultValue() throws UnknownTypeException {
        return null;
    }

    @Override
    public Object get() {
        return this.value;
    }
}
