package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.utils.ArrayUtils;

import java.util.*;

public class MObjectFieldMany extends MObjectField {

    private Collection<Object> values;

    public MObjectFieldMany(MObject owner, Field field) throws UnknownPrimitiveTypeException {
        super(owner, field);
        values = new LinkedHashSet<>();
    }

    @Override
    public void init(Object value) throws InvalidFieldValueException {

        if (!ArrayUtils.isMany(value.getClass())) {
            throw new InvalidFieldValueException("Non-array value passed to many-field");
        }

        // it's an array since it's many
        this.values.addAll(Arrays.asList(((Object[]) value)));
    }

    @Override
    public void set(Object value) throws InvalidFieldValueException {
        throw new InvalidFieldValueException("Cannot assign to many-values field " + field.name());
    }

    @Override
    public Object get() {
        return this.values;
    }

    public void check(Object mobj) throws InvalidFieldValueException {
    }

    protected Object defaultValue() throws UnknownPrimitiveTypeException {
        return new LinkedHashSet<>();
    }

    public boolean isEmpty() {
        return this.values.size() == 0;
    }

    public int size() {
        return this.values.size();
    }

    public void clear() {
        this.values.clear();
    }
}
