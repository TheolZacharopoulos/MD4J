package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class NullTypeImpl implements Type {

    public static final String NAME = "NULL";

    protected final Schema schema;

    public NullTypeImpl() {
        this(null);
    }

    public NullTypeImpl(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }
}
