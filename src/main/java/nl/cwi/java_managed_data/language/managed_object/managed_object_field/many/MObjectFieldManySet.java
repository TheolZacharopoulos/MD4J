package nl.cwi.java_managed_data.language.managed_object.managed_object_field.many;

import nl.cwi.java_managed_data.language.managed_object.MObject;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.java_managed_data.language.schema.models.definition.Field;
import nl.cwi.java_managed_data.M;
import nl.cwi.java_managed_data.language.utils.ReflectionUtils;

import java.util.*;

/**
 * Represents a multi value field which is a Set.
 * @author Theologos Zacharopoulos
 */
public class MObjectFieldManySet extends MObjectFieldMany {

    protected Map<Object, Object> values;

    public MObjectFieldManySet(MObject owner, Field field) throws UnknownTypeException {
        super(owner, field);
        this.values = defaultValue();
    }

    @Override
    public void init(Object values) throws InvalidFieldValueException, NoKeyFieldException  {
        super.init(values);

        // it's an array since it's many
        for (Object initValue : ((Collection) values)) {
            this.add(initValue);
        }
    }

    @Override
    protected Map<Object, Object> defaultValue() throws UnknownTypeException {
        return new LinkedHashMap<>();
    }

    @Override
    public Iterator iterator() {
        return this.values.values().iterator();
    }

    @Override
    public Object get() {
        return new LinkedHashSet<>(this.values.values());
    }

    @Override
    public void add(Object value) throws NoKeyFieldException {
        final Object keyValue = getKeyValue(value);
        if (value != null && keyValue != null) {
            if (this.values.get(keyValue) != value) {
                __insert(value);
            }
        }
    }

    @Override
    public void __insert(Object value) throws NoKeyFieldException {
        final Object keyValue = getKeyValue(value);
        this.values.put(keyValue, value);
    }

    @Override
    public void __delete(Object value) throws NoKeyFieldException {
        final Object keyValue = getKeyValue(value);
        this.values.remove(keyValue);
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    private Object getKeyValue(Object value) throws NoKeyFieldException {
        // since we do not support (yet) Sets of primitives,
        // this would be a managed object.
        final M mObjectNewValue = (M)value;
        final Field newValueKeyField = mObjectNewValue.schemaKlass().key();

        if (newValueKeyField == null) {
            throw new NoKeyFieldException("No key when adding " + mObjectNewValue.schemaKlass().name() + " to " + this.field.name());
        }
        return ReflectionUtils.getValueFromFieldSafe(value, newValueKeyField.name(), newValueKeyField.type().classOf());
    }

    @Override
    public void clear() {
        this.values.clear();
    }
}
