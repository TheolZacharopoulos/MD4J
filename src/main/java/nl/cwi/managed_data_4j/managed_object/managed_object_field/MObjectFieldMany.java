package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class MObjectFieldMany extends MObjectField {

    public MObjectFieldMany(MObject owner, Field field) throws UnknownPrimitiveTypeException {
        super(owner, field);
        this.value = defaultValue();
    }

    @Override
    public void init(Object value) throws InvalidFieldValueException {
        if (!value.getClass().isArray()) throw new InvalidFieldValueException("Non-array value passed to many-field");

        // it's an array since it's many
        Object[] inits = ((Object[]) value);

        // transform to a collection
        Collection<Object> valuesToSet = new HashSet<>(Arrays.asList(inits));

        // make the value object a collection, and add the values to that.
        (valuesToSet).forEach(val -> ((Collection<Object>) this.value).add(val));
    }

    @Override
    public void set(Object value) throws InvalidFieldValueException {
        throw new InvalidFieldValueException("Cannot assign to many-values field " + field.name());
    }

    @Override
    public Object get() {
        return this.value;
    }

    public void check(Object mobj) throws InvalidFieldValueException {
    }

    protected Object defaultValue() throws UnknownPrimitiveTypeException {
        return new HashSet<>();
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
