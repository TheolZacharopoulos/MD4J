package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;

public abstract class MObjectField {

    protected final MObject owner;
    protected final Field field;
    protected final Field inverse;

    /**
     * A field of the Managed Object
     * @param owner the owner Klass of the field.
     * @param field the field
     */
    public MObjectField(MObject owner, Field field) {
        this.owner = owner;
        this.field = field;

        this.inverse = field.inverse();
    }

    protected abstract void check(Object value) throws InvalidFieldValueException;
    protected abstract Object defaultValue() throws UnknownPrimitiveTypeException;
    public abstract void set(Object value) throws InvalidFieldValueException;
    public abstract Object get();
    public abstract void init(Object value) throws InvalidFieldValueException;

    public Field getField() {
        return this.field;
    }
}
