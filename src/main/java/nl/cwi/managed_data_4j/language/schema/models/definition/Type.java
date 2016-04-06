package nl.cwi.managed_data_4j.language.schema.models.definition;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Key;

/**
 * Type definitions.
 * Can be Primitive or a Klass.
 * @author Theologos Zacharopoulos
 */
public interface Type extends M {

    /**
     * A name fields that is used as a unique key
     */
    @Key
    String name(String... name);

    @Inverse(other=Type.class, field="types")
    Schema schema(Schema... schema);

    /**
     * Which Java class does Type is of.
     */
    Class<?> classOf(Class<?>... classOf);
}
