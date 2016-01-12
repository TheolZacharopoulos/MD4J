package nl.cwi.managed_data_4j.schema.models.implementation;

import nl.cwi.managed_data_4j.schema.models.definition.Primitive;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

public class PrimitiveImpl implements Primitive {

    protected String name = null;
    protected Schema schema = null;

    public PrimitiveImpl(String name) {
        this(name, null);
    }

    public PrimitiveImpl(String name, Schema schema) {
        this.name = name;
        this.schema = schema;
    }

    @Override
    public String name(String... name) {
        if (name.length > 0) {
            this.name = name[0];
        }
        return this.name;
    }

    @Override
    public Schema schema(Schema... schema) {
        if (schema.length > 0) {
            this.schema = schema[0];
        }
        return this.schema;
    }
}
