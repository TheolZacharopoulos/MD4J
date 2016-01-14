package nl.cwi.managed_data_4j.utils;

import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

public class PrimitiveUtils {

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

            default:
                throw new UnknownPrimitiveTypeException("Unknown primitive type: " + typeName);
        }
    }
}
