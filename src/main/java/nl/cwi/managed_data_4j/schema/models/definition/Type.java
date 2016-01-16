package nl.cwi.managed_data_4j.schema.models.definition;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Inverse;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Key;
import nl.cwi.managed_data_4j.schema.models.definition.annotations.Order;

/**
 * Represents a Type, can be Primitive or a Klass.
 */
public interface Type {

    /**
     * A name fields that is used as a unique key
     */
    @Order(value=1)
    @Key
    String name(String... name);

    @Order(value=2)
    @Inverse(other=Type.class, field="types")
    Schema schema(Schema... schema);
}
