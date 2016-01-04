package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;

import java.util.Collection;
import java.util.Collections;

public class MObjectFieldMany extends MObjectField {

    public MObjectFieldMany(MObject owner, Field field) throws UnknownPrimitiveTypeException {
        super(owner, field);
        this.value = defaultValue();
    }

    @Override
    public void init(Object value) throws InvalidFieldValueException {
        // make the value object a collections, and add the values on that.
        ((Collection<Object>) value).forEach(val -> ((Collection<Object>) this.value).add(val));
    }

    @Override
    public void set(Object value) throws InvalidFieldValueException {
        throw new InvalidFieldValueException("Cannot assign to many-valued field " + field.name());
    }

    @Override
    public Object get() {
        return this.value;
    }

    public void check(Object mobj) throws InvalidFieldValueException {
    }

    protected Object defaultValue() throws UnknownPrimitiveTypeException {
        return Collections.EMPTY_SET;
    }

    public boolean isEmpty() {
        return ((Collection<Object>) this.value).isEmpty();
    }

    public int size() {
        return ((Collection<Object>) this.value).size();
    }

    public void clear() {
        ((Collection<Object>) this.value).clear();
    }
}
