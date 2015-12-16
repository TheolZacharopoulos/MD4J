package nl.cwi.managed_data_4j.klass_system.models;

import java.util.HashSet;
import java.util.Set;

/**
 * A self-described Schema,
 * since every concept is used is included in its definition.
 */
public interface Schema {

    /**
     * A set of native primitive types.
     */
    Set<Type> types(Type... type);

    /**
     * A set of klasses
     */
    default Set<Klass> klasses() {
        return new HashSet<>();
    }
}