package nl.cwi.managed_data_4j.schema.load;

import nl.cwi.managed_data_4j.managed_object.managed_object_field.errors.UnknownPrimitiveTypeException;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.models.definition.Primitive;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;
import nl.cwi.managed_data_4j.utils.PrimitiveUtils;

public class TypeFactory {

    public static Type getTypeFromClass(
        Class<?> typeClass,
        Schema schema,
        SchemaFactory factory,
        SchemaLoaderCache cache) throws UnknownPrimitiveTypeException
    {

        if (PrimitiveUtils.isPrimitiveClass(typeClass)) {
            Primitive primitive = factory.primitive();
            primitive.name(typeClass.getSimpleName());
            primitive.schema(schema);
            return primitive;
        }

        if (cache.getType(typeClass.getSimpleName()) != null) {
            return cache.getType(typeClass.getSimpleName());
        }

        throw new UnknownPrimitiveTypeException("Unknown primitive type: " + typeClass.getSimpleName());
    }
}
