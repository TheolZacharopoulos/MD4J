package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.many;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a multi value field which is a Set.
 * @author Theologos Zacharopoulos
 */
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

    @Override
    public Iterator iterator() {
        return this.value.iterator();
    }

    @Override
    public void __insert(Object value) {
        this.value.add(value);
    }

    @Override
    public void __delete(Object value) {
        if (this.value.contains(value)) {
            this.value.remove(value);
        }
    }
}
