package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.many;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a multi value field which is a List.
 * @author Theologos Zacharopoulos
 */
public class MObjectFieldManyList extends MObjectFieldMany {

    private List<Object> values;

    public MObjectFieldManyList(MObject owner, Field field) throws UnknownTypeException {
        super(owner, field);
        this.values = defaultValue();
    }

    @Override
    public void init(Object values) throws InvalidFieldValueException, NoKeyFieldException {
        super.init(values);

        // it's an array since it's many
        ((Collection)values).forEach(this::add);
    }

    protected List<Object> defaultValue() throws UnknownTypeException {
        return new LinkedList<>();
    }

    @Override
    public Iterator iterator() {
        return this.values.iterator();
    }

    @Override
    public void add(Object value) {
        if (value == null) return;

        if (!this.values.contains(value)) {
            __insert(value);
            notify(value);
        }
    }

    @Override
    public void __insert(Object value) {
        this.values.add(value);
    }

    @Override
    public void __delete(Object value) {
        if (this.values.contains(value)) {
            this.values.remove(value);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public Object get() {
        return this.values;
    }

    @Override
    public void clear() {
        this.values.clear();
    }
}
