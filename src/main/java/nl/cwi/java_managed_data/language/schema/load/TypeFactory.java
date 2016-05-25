package nl.cwi.java_managed_data.language.schema.load;

import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.UnknownTypeException;
import nl.cwi.java_managed_data.language.schema.boot.SchemaFactory;
import nl.cwi.java_managed_data.language.schema.models.definition.Primitive;
import nl.cwi.java_managed_data.language.schema.models.definition.Schema;
import nl.cwi.java_managed_data.language.schema.models.definition.Type;
import nl.cwi.java_managed_data.language.primitives.PrimitivesManager;

import java.util.Map;

/**
 * Creates types
 * @author Theologos Zacharopoulos
 */
public class TypeFactory {

    /**
     * Creates a type
     *          the type can be primitive, based on the Primitives definitions
     *          the type can be klass, if it is already defined and added in the cache
     * @param typeClass the class of the type
     * @param schema the schema which the type belongs to
     * @param factory a schema factory to create new types with
     * @param cache a type cache that keeps the definitions of klass types
     * @return a new type
     * @throws UnknownTypeException if no type found
     */
    public static Type getTypeFromClass(
        Class<?> typeClass,
        Schema schema,
        SchemaFactory factory,
        Map<String, Type> cache) throws UnknownTypeException
    {
        if (PrimitivesManager.getInstance().isPrimitiveClass(typeClass)) {
            Primitive primitive = factory.Primitive();
            primitive.name(typeClass.getSimpleName());
            primitive.schema(schema);
            primitive.classOf(typeClass);
            return primitive;
        }

        if (cache.get(typeClass.getSimpleName()) != null) {
            return cache.get(typeClass.getSimpleName());
        }

        throw new UnknownTypeException("Unknown primitive type: " + typeClass.getSimpleName());
    }
}
