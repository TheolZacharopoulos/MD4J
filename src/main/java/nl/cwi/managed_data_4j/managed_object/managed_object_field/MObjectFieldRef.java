package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;

import java.lang.reflect.Proxy;

public class MObjectFieldRef extends MObjectFieldSingle {

    public MObjectFieldRef(MObject owner, Field field)
            throws UnknownPrimitiveTypeException, InvalidFieldValueException
    {
        super(owner, field);
    }

    @Override
    public void check(Object mObj) throws InvalidFieldValueException {
        // Since it's here (Ref) is a managed object (MObject).
        this.value = (MObject) Proxy.getInvocationHandler(mObj);

        final Klass valueSchemaKlass = ((MObject)this.value).getSchemaKlass();
        final Klass fieldType = (Klass) this.field.type();

        // TODO: Check also subclasses
        boolean isSubKlass = true;

        if (!valueSchemaKlass.name().equals(fieldType.name()) || !isSubKlass) {
            throw new InvalidFieldValueException(
                "Invalid value for " + this.field.owner().name() + " " +
                this.field.name() + " " + field.type().name() + " found (" + valueSchemaKlass.name() + ")");
        }
    }
}
