package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.single;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.many.MObjectFieldMany;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.M;
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
        notify(get(), value);
        __set(value);
    }

    public void __set(Object value) {
        this.value = value;
    }

    /**
     * Notify any dependencies for the change of that field.
     * @param oldValue the old value of the field
     * @param newValue the old value of the field
     */
    private void notify(Object oldValue, Object newValue) {
        // if the new value is the same as the old one, then nothing changes
        if (oldValue == newValue) return;

        // if there is an inverse field for this field
        if (inverse != null) {

            // if it is many, then old and new are both collections
            if (inverse.many()) {

                // delete owner from old reference
                if (oldValue != null) {
                    final MObject oldValueMObject = (MObject) Proxy.getInvocationHandler(oldValue);
                    final MObjectFieldMany oldValueMObjectInverseField = (MObjectFieldMany) oldValueMObject._getField(inverse.name());
                    oldValueMObjectInverseField.__delete(owner.getProxy());
                }

                // insert owner to new inverse reference
                if (newValue != null) {
                    final MObject newValueMObject = (MObject) Proxy.getInvocationHandler(newValue);
                    final MObjectFieldMany newValueMObjectInverseField = (MObjectFieldMany) newValueMObject._getField(inverse.name());

                    newValueMObjectInverseField.__insert(owner.getProxy());
                }

            } else { // otherwise, old and new are both managed objects

                // set old inverse reference to null
                if (oldValue != null) {
                    final MObject newValueMObject = (MObject) Proxy.getInvocationHandler(newValue);
                    final MObjectFieldRef newValueMObjectInverseField = (MObjectFieldRef) newValueMObject._getField(inverse.name());
                    newValueMObjectInverseField.__set(null);
//                    setValueToField(newValue, inverse.name(), inverse.type(), null);
                }

                // set new inverse reference to owner
                if (newValue != null) {
                    final MObject newValueMObject = (MObject) Proxy.getInvocationHandler(newValue);
                    final MObjectFieldRef newValueMObjectInverseField = (MObjectFieldRef) newValueMObject._getField(inverse.name());
                    newValueMObjectInverseField.__set(owner.getProxy());

                    // TODO: That throws stack overflow
//                    setValueToField(newValue, inverse.name(), inverse.type(), owner.getProxy());
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
}
