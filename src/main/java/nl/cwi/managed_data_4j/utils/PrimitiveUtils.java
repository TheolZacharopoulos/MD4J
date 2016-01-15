package nl.cwi.managed_data_4j.utils;

import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

public class PrimitiveUtils {

    public static boolean isPrimitiveValue(String typeName, Object value) {
        boolean ok = false;

        switch (typeName) {
            case "String":
                if (value instanceof String) ok = true;
                break;

            case "Integer":
            case "int":
                if (value instanceof Integer) ok = true;
                break;

            case "Boolean":
            case "boolean":
                if (value instanceof Boolean) ok = true;
                break;

            case "Float":
                if (value instanceof Float) ok = true;
                break;

            case "Double":
                if (value instanceof Double) ok = true;
                break;

            case "Class":
                if (value instanceof Class) ok = true;
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

       return ok;
    }

    public static boolean isPrimitive(Type type) {

        switch (type.name()) {
            case "String":
                return true;

            case "Integer":
            case "int":
                return true;

            case "Boolean":
            case "boolean":
                return true;

            case "Float":
                return true;

            case "Double":
                return true;

            case "Class":
                return true;
        }

        return false;
    }

    public static Object primitiveDefault(String typeName) throws UnknownPrimitiveTypeException {
        switch (typeName) {
            case "String":
                return "";

            case "Integer":
            case "int":
                return 0;

            case "Boolean":
            case "bool":
                return false;

            case "Float":
            case "Double":
                return 0.0;

            case "Class":
                return null;

            default:
                throw new UnknownPrimitiveTypeException("Unknown primitive type: " + typeName);
        }
    }
}
