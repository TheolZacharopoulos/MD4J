package nl.cwi.managed_data_4j.klass_system.models.schema_schema;

/**
 * Represents a TypeImpl, can be PrimitiveImpl or a KlassImpl.
 */
public interface Type {

    /**
     * A name fields that is used as a unique key
     */
    String name(String... name);

    @Inverse(other=Type.class, field="types")
    Schema schema(Schema... schema);
}
