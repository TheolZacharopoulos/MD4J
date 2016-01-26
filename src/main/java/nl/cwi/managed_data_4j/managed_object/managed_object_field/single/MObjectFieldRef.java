package nl.cwi.managed_data_4j.managed_object.managed_object_field.single;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;

import java.lang.reflect.Proxy;

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
        this.value = value;
    }

    @Override
    public void check(Object mObj) throws InvalidFieldValueException {

        // Check for optional
        if (!this.field.optional()) {
            if (mObj == null) {
                throw new InvalidFieldValueException(
                    "Cannot assign null to non-optional field of klass '" +
                    field.owner().name() + "' with name '" + field.name() + "'");
            }
        }

        if (!Proxy.isProxyClass(mObj.getClass())) {
            throw new RuntimeException(
                "The value '" + mObj + "' of type '" +
                mObj.getClass() + "' should be proxied since its a Managed object.");
        }

        final Klass valueSchemaKlass = ((MObject) Proxy.getInvocationHandler(mObj)).schemaKlass();
        final Klass fieldType = (Klass) this.field.type();

        boolean isSubKlass = false;
        if (fieldType.subklasses() != null) {
            for (Klass superKlass : valueSchemaKlass.supers()) {
                if (superKlass != null && superKlass.name().equals(fieldType.name())) {
                    isSubKlass = true;
                }
            }
        }

        if (! (valueSchemaKlass.name().equals(fieldType.name()) || isSubKlass)) {
            throw new InvalidFieldValueException(
                "Invalid value for " + this.field.owner().name() + " " +
                this.field.name() + " " + field.type().name() + " found (" + valueSchemaKlass.name() + ")");
        }
    }
}
