package nl.cwi.managed_data_4j.managed_object.managed_object_field;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;

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
                if (this.value instanceof String) ok = true;
                break;

            case "Integer":
                if (this.value instanceof Integer) ok = true;
                break;

            case "Boolean":
                if (this.value instanceof Boolean) ok = true;
                break;

            case "Float":
                if (this.value instanceof Float) ok = true;
                break;

            case "Double":
                if (this.value instanceof Double) ok = true;
                break;

            case "Object":
                if (this.value instanceof String ||
                    this.value instanceof Integer ||
                    this.value instanceof Boolean ||
                    this.value instanceof Float ||
                    this.value instanceof Double)
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
        Object defaultValue = null;

        switch (this.field.type().name()) {
            case "String":
                defaultValue = "";
                break;

            case "Integer":
                defaultValue = 0;
                break;

            case "Boolean":
                defaultValue = false;
                break;

            case "Float":
            case "Double":
                defaultValue = 0.0;
                break;

            case "Object":
                defaultValue = null;
                break;

            default:
                throw new UnknownPrimitiveTypeException("Unknown primitive type: " + field.type().name());
        }

        return defaultValue;
    }
}
