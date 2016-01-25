package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;

/**
 * Represents the field of a managed object.
 * @author Theologos Zacharopoulos
 */
public abstract class MObjectField<T> {

    protected final MObject owner;
    protected final Field field;
    protected final Field inverse;

    protected T value;

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

    /**
     * Initializes the field with a value
     * @param value the initialization value
     * @throws InvalidFieldValueException when an invalid value for this field is given
     */
    public abstract void init(T value) throws InvalidFieldValueException;

    /**
     * Checks the given value if it is valid
     * @param value the initialization value
     * @throws InvalidFieldValueException when an invalid value for this field is given
     */
    protected abstract void check(T value) throws InvalidFieldValueException;

    /**
     * Returns a default value for this kind of field.
     * @return a default value
     * @throws UnknownTypeException when the field type name is unknown.
     */
    protected abstract T defaultValue() throws UnknownTypeException;

    /**
     * Sets a value to the field.
     * @param value the initialization value
     * @throws InvalidFieldValueException when an invalid value for this field is given
     */
    public abstract void set(T value) throws InvalidFieldValueException;

    /**
     * Returns the value of the field
     * @return the fields value.
     */
    public abstract T get();

    /**
     * Returns the Field object that is wrapped.
     * @return the Field object.
     */
    public Field getField() {
        return this.field;
    }
}
