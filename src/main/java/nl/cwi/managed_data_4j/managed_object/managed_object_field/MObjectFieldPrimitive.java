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
        boolean ok = false;

        switch (this.field.type().name()) {
            case "String":
                if (value instanceof String) ok = true;
                break;

            case "Integer":
                if (value instanceof Integer) ok = true;
                break;

            case "Boolean":
                if (value instanceof Boolean) ok = true;
                break;

            case "Float":
                if (value instanceof Float) ok = true;
                break;

            case "Double":
                if (value instanceof Double) ok = true;
                break;

            case "Object":
                if (value instanceof String ||
                    value instanceof Integer ||
                    value instanceof Boolean ||
                    value instanceof Float ||
                    value instanceof Double)
                {
                    ok = true;
                }
                break;
        }

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
