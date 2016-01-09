package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.boot.boot_primitives.BoolPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.IntegerPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.ObjectPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.StringPrimitive;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class TypeFactory {

    public static Type getTypeFromClass(Class<?> typeClass, Schema schema) {

        switch (typeClass.getSimpleName()) {

            // Primitives
            case "String":
                return new StringPrimitive(schema);

            case "Integer":
            case "int":
                return new IntegerPrimitive(schema);

            case "Boolean":
            case "boolean":
                return new BoolPrimitive(schema);

            case "Float":
                return new IntegerPrimitive(schema);

            case "Double":
                return new IntegerPrimitive(schema);

            case "Object":
                return new ObjectPrimitive(schema);
        }

        return new LoadNullType(schema);
    }
}
