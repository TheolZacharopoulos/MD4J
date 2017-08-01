package nl.cwi.managed_data_4j.language.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;

import java.util.LinkedHashSet;
import java.util.Set;

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
    protected Field inverse;

    protected Set<Klass> fieldOwnerSuperKlass;

    /**
     * A field of the Managed Object
     * @param owner the owner Klass of the field.
     * @param field the field
     */
    public MObjectField(MObject owner, Field field) {
        this.owner = owner;
        this.field = field;

        this.inverse = field.inverse();
        this.fieldOwnerSuperKlass = getAllSuperKlasses(this.field.owner());

        this.buildInverse();
    }

    /**
     * Initializes the field with a value
     * @param value the initialization value
     * @throws InvalidFieldValueException when an invalid value for this field is given.
     * @throws NoKeyFieldException if no key field found.
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
     * @throws NoKeyFieldException if no key field found.
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

    protected Set<Klass> getAllSuperKlasses(Klass klass) {
        Set<Klass> supers = new LinkedHashSet<>();
        getAllSuperKlasses(klass, supers);
        return supers;
    }

    /**
     * Helper method
     * In case no inverse exist,
     * check for inverse on the parent klasses
     */
    private void buildInverse() {

        if (this.inverse == null) {
            for (Klass aSuper : this.fieldOwnerSuperKlass) {
                for (Field aSuperField : aSuper.fields()) {
                    if (aSuperField.name().equals(field.name()) && aSuperField.inverse() != null) {
                        this.inverse = aSuperField.inverse();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Helper method
     * Returns all super klasses from a klass
     * @param klass the Klass
     * @param stack a stack that tracks the super classes
     */
    protected void getAllSuperKlasses(Klass klass, Set<Klass> stack) {
        if (klass.supers() != null) {
            for (Klass superKlass : klass.supers()) {

                if (superKlass.supers() != null && superKlass.supers().size() > 0) {
                    stack.add(superKlass);
                    getAllSuperKlasses(superKlass, stack);
                } else {
                    stack.add(superKlass);
                }
            }
        }
    }
}
