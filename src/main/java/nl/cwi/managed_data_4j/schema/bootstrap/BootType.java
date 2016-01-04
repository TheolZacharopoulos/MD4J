package nl.cwi.managed_data_4j.schema.bootstrap;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class BootType implements Type {

    private Schema schema;

    public BootType() {
        this(null);
    }

    public BootType(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String name(String... name) {
        return Type.class.getSimpleName();
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
