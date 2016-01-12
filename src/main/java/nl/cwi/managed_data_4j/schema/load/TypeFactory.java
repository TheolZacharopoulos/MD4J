package nl.cwi.managed_data_4j.schema.load;

import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.definition.Primitive;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;
import java.util.List;
import java.util.Set;

public class TypeFactory {

    public static Type getTypeFromClass(
        Class<?> typeClass,
        Schema schema,
        SchemaFactory factory,
        SchemaLoaderCache cache) throws UnknownPrimitiveTypeException
    {

        if (typeClass == String.class) {
            Primitive primitive = factory.primitive("String");
            primitive.schema(schema);
            return primitive;
        }

        if (typeClass == Integer.class || typeClass == int.class) {
            Primitive primitive = factory.primitive("Integer");
            primitive.schema(schema);
            return primitive;
        }

        if (typeClass == Boolean.class || typeClass == boolean.class) {
            Primitive primitive = factory.primitive("Boolean");
            primitive.schema(schema);
            return primitive;
        }

        if (typeClass == Float.class) {
            Primitive primitive = factory.primitive("Float");
            primitive.schema(schema);
            return primitive;
        }

        if (typeClass == Double.class) {
            Primitive primitive = factory.primitive("Double");
            primitive.schema(schema);
            return primitive;
        }

        if (typeClass == Set.class) {
            Primitive primitive = factory.primitive("Set");
            primitive.schema(schema);
            return primitive;
        }

        if (typeClass == List.class) {
            Primitive primitive = factory.primitive("List");
            primitive.schema(schema);
            return primitive;
        }

        if (cache.getType(typeClass.getSimpleName()) != null) {
            return cache.getType(typeClass.getSimpleName());
        }

        throw new UnknownPrimitiveTypeException("Unknown primitive type: " + typeClass.getSimpleName());
    }
}
