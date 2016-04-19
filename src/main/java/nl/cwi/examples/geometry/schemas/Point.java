package nl.cwi.examples.geometry.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.M;

/**
 * The Point Schema.
 *
 * This schema is managed by a data manager capable of initialization
 * allowing the objects (points) to be created with starting props.
 */
public interface Point extends M {
    default void testVoid(String message) {
        System.out.println(message);
    }

    default Integer testInt() {
        System.out.println(333);
        return 333;
    }
}