package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;

public abstract class MObjectFieldSingle extends MObjectField<Object> {

    public MObjectFieldSingle(MObject owner, Field field)
            throws UnknownTypeException, InvalidFieldValueException
    {
        super(owner, field);
        this.value = defaultValue();
    }

    public void init(Object value) throws InvalidFieldValueException {
        this.set(value);
    }

    @Override
    public void set(Object value) throws InvalidFieldValueException {
        if (value != null) {
            this.check(value);
        }
        this.value = value;
    }

    @Override
    protected Object defaultValue() throws UnknownTypeException {
        return null;
    }
}
