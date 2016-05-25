package nl.cwi.java_managed_data.language.managed_object.managed_object_field.single;

import nl.cwi.java_managed_data.language.managed_object.MObject;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.java_managed_data.language.schema.models.definition.Field;
import nl.cwi.java_managed_data.language.primitives.PrimitivesManager;

/**
 * Represents a single value field which is a Primitive.
 * @author Theologos Zacharopoulos
 */
public class MObjectFieldSinglePrimitive extends MObjectFieldSingle {

    public MObjectFieldSinglePrimitive(MObject owner, Field field)
            throws UnknownTypeException, InvalidFieldValueException
    {
        super(owner, field);
    }

    @Override
    public void check(Object value) throws InvalidFieldValueException {
        boolean ok = PrimitivesManager.getInstance().isPrimitiveValue(this.field.type().name(), value);

        if (!ok) {
            throw new InvalidFieldValueException(
                "Invalid value '" + value + "', of type '" + value.getClass() +
                "' for field '" + field.name() + "', of type '" + field.type().name());
        }
    }

    @Override
    protected Object defaultValue() throws UnknownTypeException {
        return PrimitivesManager.getInstance().getDefaultValueForPrimitive(field.type().classOf());
    }
}
