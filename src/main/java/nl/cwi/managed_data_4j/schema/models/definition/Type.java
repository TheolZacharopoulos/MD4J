package nl.cwi.managed_data_4j.schema.models.definition;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Key;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Optional;

/**
 * Represents a Type, can be Primitive or a Klass.
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
