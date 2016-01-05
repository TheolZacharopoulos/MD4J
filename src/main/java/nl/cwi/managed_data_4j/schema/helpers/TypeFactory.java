package nl.cwi.managed_data_4j.schema.helpers;

import nl.cwi.managed_data_4j.schema.boot.boot_primitives.BoolPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.IntegerPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.ObjectPrimitive;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.StringPrimitive;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class TypeFactory {
    public static Type getTypeFromClassName(String className) {
        switch (className) {
            case "String":
                return new StringPrimitive(null); // TODO

            case "Integer":
                return new IntegerPrimitive(null); // TODO

            case "Boolean":
                return new BoolPrimitive(null); // TODO

            case "Float":
                return new IntegerPrimitive(null); // TODO

            case "Double":
                return new IntegerPrimitive(null); // TODO

            case "Object":
                return new ObjectPrimitive(null); // TODO

        }
        return null;
    }
}
