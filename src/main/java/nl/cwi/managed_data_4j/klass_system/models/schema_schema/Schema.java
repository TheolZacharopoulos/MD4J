package nl.cwi.managed_data_4j.klass_system.models.schema_schema;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * A self-described Schema,
 * since every concept is used is included in its definition.
 */
public interface Schema {

    /**
     * The Interface which describes this schema.
     */
    Set<Class> klassInterfaces(Class... interfaces);

    /**
     * A set of all the types in the schema.
     */
    Set<Type> types(Type... type);

    /**
     * A set of klasses types.
     */
    default Set<Klass> klasses() {
        return types().stream()
            .filter(type -> type.name().equals("Klass"))
            .map(Klass.class::cast)
            .collect(Collectors.toSet());
    }

    /**
     * A set of primitive types.
     */
    default Set<Primitive> primitives() {
        return types().stream()
            .filter(type -> type.name().equals("Primitive"))
            .map(Primitive.class::cast)
            .collect(Collectors.toSet());
    }
}