package nl.cwi.managed_data_4j.managed_object.managed_object_field.many;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a multi value field which is a List.
 * @author Theologos Zacharopoulos
 */
@Deprecated
public class MObjectFieldManyList extends MObjectFieldMany<List<Object>> {

    public MObjectFieldManyList(MObject owner, Field field) throws UnknownTypeException {
        super(owner, field);
        this.value = defaultValue();
    }

    @Override
    public void init(List<Object> values) throws InvalidFieldValueException {
        super.init(values);

        // it's an array since it's many
        this.value.addAll(values);
    }

    protected List<Object> defaultValue() throws UnknownTypeException {
        return new LinkedList<>();
    }

    @Override
    public Iterator iterator() {
        return this.value.iterator();
    }
}
