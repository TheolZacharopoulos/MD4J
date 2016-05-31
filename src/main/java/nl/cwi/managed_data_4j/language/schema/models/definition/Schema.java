package nl.cwi.managed_data_4j.language.schema.models.definition;

import java.util.Set;
import java.util.stream.Collectors;

import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

/**
 * Schema definition.
 *
 * While there are many kinds of schemas and data managers
 * there is one basic schema that is used in the implementation of itself.
 * This schema is called the Schema Schema.
 * This is a self-described Schema,
 * since every concept is used is included in its definition.
 *
 * @author Theologos Zacharopoulos
 */
public interface Schema extends M {

    /**
     * A set of all the types in the schema.
     */
    @Contain
    Set<Type> types(Type... type);

    /**
     * A set of klasses types.
     */
    default Set<Klass> klasses(Klass... klass) {
        return types().stream()
            .filter(type -> type.schemaKlass().name().equals("Klass"))
            .map(Klass.class::cast)
            .collect(Collectors.toSet());
    }

    /**
     * A set of primitive types.
     */
    default Set<Primitive> primitives(Primitive... primitive) {
        return types().stream()
                .filter(type -> type.schemaKlass().name().equals("Primitive"))
            .map(Primitive.class::cast)
            .collect(Collectors.toSet());
    }
}