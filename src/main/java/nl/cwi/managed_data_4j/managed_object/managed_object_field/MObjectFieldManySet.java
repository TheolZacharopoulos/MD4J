package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;

import java.util.LinkedHashSet;
import java.util.Set;

public class MObjectFieldManySet extends MObjectFieldMany<Set<Object>> {

    public MObjectFieldManySet(MObject owner, Field field) throws UnknownTypeException {
        super(owner, field);
        this.value = defaultValue();
    }

    @Override
    public void init(Set<Object> values) throws InvalidFieldValueException {
        super.init(values);

        // it's an array since it's many
        this.value.addAll(values);
    }

    protected Set<Object> defaultValue() throws UnknownTypeException {
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
