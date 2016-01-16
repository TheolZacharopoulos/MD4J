package nl.cwi.managed_data_4j.schema.models.definition;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Order;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * While there are many kinds of schemas and data managers
 * there is one basic schema that is used in the implementation of itself.
 * This schema is called the Schema Schema.
 *
 * This is a self-described Schema,
 * since every concept is used is included in its definition.
 */

public interface Schema {

    /**
     * A set of all the types in the schema.
     */
    @Contain
    @Order(value=1)
    Set<Type> types(Type... type);

    /**
     * A set of klasses types.
     */
    @Order(value=2)
    default Set<Klass> klasses() {
        return types().stream()
            .filter(Klass.class::isInstance)
            .map(Klass.class::cast)
            .collect(Collectors.toSet());
    }

    /**
     * A set of primitive types.
     */
    @Order(value=3)
    default Set<Primitive> primitives() {
        return types().stream()
            .filter(Primitive.class::isInstance)
            .map(Primitive.class::cast)
            .collect(Collectors.toSet());
    }
}