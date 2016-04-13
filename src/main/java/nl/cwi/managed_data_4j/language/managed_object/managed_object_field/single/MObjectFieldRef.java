package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.single;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.Type;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Represents a single value field which is a Managed Object.
 * @author Theologos Zacharopoulos
 */
public class MObjectFieldRef extends MObjectFieldSingle {

    public MObjectFieldRef(MObject owner, Field field)
            throws UnknownTypeException, InvalidFieldValueException
    {
        super(owner, field);
    }

    @Override
    public void set(Object value) throws InvalidFieldValueException {
        if (value != null) {
            this.check(value);
        }
        notify(get(), value);
        __set(value);
    }

    public void __set(Object value) {
        this.value = value;
    }

    private void notify(Object oldValue, Object newValue) {
        // if the new value is the same as the old one, then nothing changes
        if (oldValue == newValue) return;

        // if there is an inverse field for this field
        if (inverse != null) {

            // if it is many, then old and new are both collections
            if (inverse.many()) {

                // set old inverse reference to null
                if (oldValue != null) {
                    // TODO: delete (owner) from oldValue
                    setValueToField(newValue, inverse.name(), inverse.type(), null);
                }

                // set new inverse reference to owner
                if (newValue != null) {
                    // TODO: add (owner) to newValue
//                    setValueToField(newValue, inverse.name(), inverse.type(), owner.getProxy());
                }

            } else { // otherwise, old and new are both managed objects

                // set old inverse reference to null
                if (oldValue != null) {
                    setValueToField(newValue, inverse.name(), inverse.type(), null);
                }

                // set new inverse reference to owner
                if (newValue != null) {
                    setValueToField(newValue, inverse.name(), inverse.type(), owner.getProxy());
                }
            }
        }
    }

    @Override
    public void check(Object obj) throws InvalidFieldValueException {

        // Check for optional
        if (!this.field.optional()) {
            if (obj == null) {
                throw new InvalidFieldValueException(
                    "Cannot assign null to non-optional field of klass '" +
                    field.owner().name() + "' with name '" + field.name() + "'");
            }
        }

        // cast it to managed object (should a managed object since it is here)
        final M managedObject = (M) obj;

        final Klass valueSchemaKlass = managedObject.schemaKlass();
        final Klass fieldType = (Klass) this.field.type();

        boolean isSubKlass = false;
        if (fieldType.subKlasses() != null) {
            for (Klass superKlass : valueSchemaKlass.supers()) {
                if (superKlass != null && superKlass.name().equals(fieldType.name())) {
                    isSubKlass = true;
                }
            }
        }

        if (!(valueSchemaKlass.name().equals(fieldType.name()) || isSubKlass)) {
            throw new InvalidFieldValueException(
                "Invalid value for " + this.field.owner().name() + " " +
                this.field.name() + " " + field.type().name() + " found (" + valueSchemaKlass.name() + ")");
        }
    }

    /**
     * Sets a value to a field reflectively
     * @param instance the instance of the object to set the field to
     * @param fieldName the field name of the field
     * @param fieldType the type of the field
     * @param value the value to set
     */
    private void setValueToField(Object instance, String fieldName, Type fieldType, Object value) {
        try {
            Method method;

            // Get params method params.
            Class<?> parameterType = Array.newInstance((fieldType).classOf(), 0).getClass();
            method = instance.getClass().getMethod(fieldName, parameterType);

            // needs to be accessible in order to invoke it
            method.setAccessible(true);

            // We need the following in order to invoke the method with this kind of parameter
            // Get the parameter type, and get the first one
            final Class<?>[] parameterTypes = method.getParameterTypes();
            final Class<?> firstParameterType = parameterTypes[0];

            // In case of vargs, make it from array to single type
            // after, create an empty array of that type, this way we can invoke methods that need
            // empty vargs as parameters.
            Object arrayOfParameterType;
            if (firstParameterType.isArray()) {
                arrayOfParameterType = Array.newInstance(firstParameterType.getComponentType(), 1);
            } else {
                arrayOfParameterType = Array.newInstance(firstParameterType, 1);
            }

            // push the value to the parameter type array.
            Array.set(arrayOfParameterType, 0, value);

            // invoke the method to set the field value
            method.invoke(instance, arrayOfParameterType);

        } catch (Throwable e) {}
    }
}
