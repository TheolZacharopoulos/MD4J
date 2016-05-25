package nl.cwi.java_managed_data.language.schema.models.definition;

import nl.cwi.java_managed_data.M;
import nl.cwi.java_managed_data.language.schema.models.definition.annotations.Inverse;
import nl.cwi.java_managed_data.language.schema.models.definition.annotations.Key;

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

    @Inverse(other=Schema.class, field="types")
    Schema schema(Schema... schema);

    /**
     * Which Java class does Type is of.
     */
    Class<?> classOf(Class<?>... classOf);
}
