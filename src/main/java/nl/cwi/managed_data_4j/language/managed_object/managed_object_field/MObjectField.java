package nl.cwi.managed_data_4j.language.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;

/**
 * Represents the field of a managed object.
 * @author Theologos Zacharopoulos
 */
public abstract class MObjectField {

    // the owner of the field as an Managed Object.
    protected final MObject owner;

    // the Field.
    protected final Field field;

    // the Inverse of the field.
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

    /**
     * Initializes the field with a value
     * @param value the initialization value
     * @throws InvalidFieldValueException when an invalid value for this field is given
     */
    public abstract void init(Object value) throws InvalidFieldValueException, NoKeyFieldException;

    /**
     * Checks the given value if it is valid
     * @param value the initialization value
     * @throws InvalidFieldValueException when an invalid value for this field is given
     */
    protected abstract void check(Object value) throws InvalidFieldValueException;

    /**
     * Returns a default value for this kind of field.
     * @return a default value
     * @throws UnknownTypeException when the field type name is unknown.
     */
    protected abstract Object defaultValue() throws UnknownTypeException;

    /**
     * Sets a value to the field.
     * @param value the initialization value
     * @throws InvalidFieldValueException when an invalid value for this field is given
     */
    public abstract void set(Object value) throws InvalidFieldValueException, NoKeyFieldException;

    /**
     * Returns the value of the field
     * @return the fields value.
     */
    public abstract Object get();

    /**
     * Returns the Field object that is wrapped.
     * @return the Field object.
     */
    public Field getField() {
        return this.field;
    }
}
