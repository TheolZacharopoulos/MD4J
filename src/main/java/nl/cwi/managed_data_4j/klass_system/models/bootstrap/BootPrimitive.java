package nl.cwi.managed_data_4j.klass_system.models.bootstrap;

import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Primitive;
import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Schema;

public class BootPrimitive implements Primitive {

    private Schema schema;

    public BootPrimitive() {
        this(null);
    }

    public BootPrimitive(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String name(String... name) {
        return Primitive.class.getSimpleName();
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }
}
