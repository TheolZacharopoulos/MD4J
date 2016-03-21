package nl.cwi.managed_data_4j.language.schema.models.definition;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Key;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Optional;

/**
 * Type definitions.
 * Can be Primitive or a Klass.
 * @author Theologos Zacharopoulos
 */
public interface Type {

    /**
     * A name fields that is used as a unique key
     */
    @Key
    String name(String... name);

    @Inverse(other=Type.class, field="types")
    Schema schema(Schema... schema);

    @Optional
    default Field key(Field... key) {
        return null;
    }
}
