package nl.cwi.managed_data_4j.language.schema.models.definition;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Key;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Optional;

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

    /**
    * The field which may represent the key for this Type.
    */
    @Optional
    Field key(Field... key);
}
