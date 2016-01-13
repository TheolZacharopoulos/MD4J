package nl.cwi.managed_data_4j.utils;

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
}
