package utils;

import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

import java.util.Set;

public class TestHelper {

    public static Klass getKlass(Set<Type> schemaTypes, String className) {
        Klass schemaKlass = null;
        for (Type schemaType : schemaTypes) {
            if (schemaType.name().equals(className))
                schemaKlass = (Klass) schemaType;
        }
        return schemaKlass;
    }

    public static Field getField(Set<Field> fields, String name) {
        Field klassField = null;
        for (Field field : fields) {
            if (field.name().equals(name))
                klassField = field;
        }
        return klassField;
    }
}
