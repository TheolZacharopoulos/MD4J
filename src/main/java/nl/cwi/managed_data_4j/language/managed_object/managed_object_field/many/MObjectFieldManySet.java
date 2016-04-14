package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.many;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.utils.ReflectionUtils;

import java.util.*;

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
        if (!findValue(value).isPresent()) {
            this.value.add(value);
        }
    }

    @Override
    public void __delete(Object value) {
        final Optional<Object> existingValue = findValue(value);
        if (existingValue.isPresent()) {
            this.value.remove(existingValue.get());
        }
    }

    private Optional<Object> findValue(Object value) {

        // since we do not support (yet) Sets of primitives,
        // this would be a managed object.
        final M mObjectNewValue = (M)value;
        final Field newValueKeyField = mObjectNewValue.schemaKlass().key();

        // in order to insert values in the set, we need to check first if
        // the value already exists, Java's Set ca not do that since the objects are proxied
        // so this is the place that we need to use the, in order to check for duplicates
        if (newValueKeyField != null) {
            for (Object existingValue : this.value) {
                final Field existingValueKeyField = ((M)existingValue).schemaKlass().key();
                if (existingValueKeyField != null) {

                    final Object existingValueKeyValue =
                            ReflectionUtils.getValueFromFieldSafe(existingValue, existingValueKeyField.name(), existingValueKeyField.type());
                    final Object newValueKeyValue =
                            ReflectionUtils.getValueFromFieldSafe(value, newValueKeyField.name(), newValueKeyField.type());

                    if (newValueKeyValue != null && existingValueKeyValue != null &&
                            existingValueKeyValue.equals(newValueKeyValue))
                    {
                        return Optional.of(existingValue);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
