package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.utils.PrimitiveUtils;

public class MObjectFieldPrimitive extends MObjectFieldSingle {

    public MObjectFieldPrimitive(MObject owner, Field field)
            throws UnknownPrimitiveTypeException, InvalidFieldValueException
    {
        super(owner, field);
    }

    @Override
    public void check(Object value) throws InvalidFieldValueException {
        boolean ok = PrimitiveUtils.isPrimitiveValue(this.field.type().name(), value);

        if (!ok) {
            throw new InvalidFieldValueException(
                "Invalid value for " + field.name() + " : " + field.type().name() +
                " = " + value + " " + value.getClass().getSimpleName());
        }
    }

    @Override
    protected Object defaultValue() throws UnknownPrimitiveTypeException {
        return PrimitiveUtils.primitiveDefault(this.field.type().name());
    }
}
