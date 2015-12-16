package nl.cwi.managed_data_4j.klass_system.models;

/**
 * Represents a Type, can be Primitive or a Klass.
 */
public interface Type {

    /**
     * A name fields that is used as a unique key
     */
    String name(String... name);
}
