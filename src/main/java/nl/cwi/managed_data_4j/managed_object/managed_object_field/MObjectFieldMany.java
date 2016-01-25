package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.utils.ArrayUtils;

import java.util.*;

public class MObjectFieldMany extends MObjectField<Collection<Object>> {

    public MObjectFieldMany(MObject owner, Field field) throws UnknownTypeException {
        super(owner, field);
        this.value = new LinkedHashSet<>();
    }

    @Override
    public void init(Collection<Object> values) throws InvalidFieldValueException {

        if (!ArrayUtils.isMany(values.getClass())) {
            throw new InvalidFieldValueException("Non-array value passed to many-field");
        }

        // it's an array since it's many
        this.value.addAll(values);
    }

    @Override
    public void set(Collection<Object> value) throws InvalidFieldValueException {
        throw new InvalidFieldValueException("Cannot assign to many-values field " + field.name());
    }

    @Override
    public Collection<Object> get() {
        return this.value;
    }

    public void check(Collection<Object> mobj) throws InvalidFieldValueException {}

    protected Collection<Object> defaultValue() throws UnknownTypeException {
        return new LinkedHashSet<>();
    }

    public boolean isEmpty() {
        return this.value.size() == 0;
    }

    public int size() {
        return this.value.size();
    }

    public void clear() {
        this.value.clear();
    }
}
