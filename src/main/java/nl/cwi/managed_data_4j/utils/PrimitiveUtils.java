package nl.cwi.managed_data_4j.utils;

import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    public static boolean isPrimitiveClass(Class<?> typeClass) {
        if (String.class.isAssignableFrom(typeClass)) return true;
        if (Integer.class.isAssignableFrom(typeClass)) return true;
        if (int.class.isAssignableFrom(typeClass)) return true;
        if (Boolean.class.isAssignableFrom(typeClass)) return true;
        if (boolean.class.isAssignableFrom(typeClass)) return true;
        if (Float.class.isAssignableFrom(typeClass)) return true;
        if (Double.class.isAssignableFrom(typeClass)) return true;
        if (Class.class.isAssignableFrom(typeClass)) return true;
        if (Set.class.isAssignableFrom(typeClass)) return true;
        if (List.class.isAssignableFrom(typeClass)) return true;

        return false;
    }

    public static boolean isPrimitive(String typeName) {

        switch (typeName) {
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

            case "Set":
                return true;

            case "List":
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

            case "Set":
                return Collections.emptySet();

            case "List":
                return Collections.emptyList();

            default:
                throw new UnknownPrimitiveTypeException("Unknown primitive type: " + typeName);
        }
    }
}
