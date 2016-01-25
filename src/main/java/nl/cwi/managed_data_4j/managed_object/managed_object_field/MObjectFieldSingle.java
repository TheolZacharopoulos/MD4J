package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;

public abstract class MObjectFieldSingle extends MObjectField {

    protected Object value;

    public MObjectFieldSingle(MObject owner, Field field)
            throws UnknownPrimitiveTypeException, InvalidFieldValueException
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
    public Object get() {
        return this.value;
    }

    @Override
    protected Object defaultValue() throws UnknownPrimitiveTypeException {
        return null;
    }
}
