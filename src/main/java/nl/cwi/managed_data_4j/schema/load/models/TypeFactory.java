package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.boot.boot_types.boot_primitives.*;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

public class TypeFactory {

    public static Type getTypeFromClass(Class<?> typeClass, Schema schema) {

        if (typeClass == String.class) {
            // TODO: Use the factory
            return new StringPrimitive(schema);
        }

        if (typeClass == Integer.class || typeClass == int.class) {
            // TODO: Use the factory
            return new IntegerPrimitive(schema);
        }

        if (typeClass == Boolean.class || typeClass == boolean.class) {
            // TODO: Use the factory
            return new BoolPrimitive(schema);
        }

        if (typeClass == Float.class) {
            // TODO: Use the factory
            return new IntegerPrimitive(schema);
        }

        if (typeClass == Double.class) {
            // TODO: Use the factory
            return new IntegerPrimitive(schema);
        }

        // TODO: remove this
        // TODO: default case load at the cache
        // TODO: otherwise throw!
        return new LoadNullType(schema);
    }
}
