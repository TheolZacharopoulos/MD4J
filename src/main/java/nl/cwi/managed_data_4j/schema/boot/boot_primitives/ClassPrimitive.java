package nl.cwi.managed_data_4j.schema.boot.boot_primitives;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Primitive;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

public class ClassPrimitive implements Primitive {

    private Schema schema;

    public ClassPrimitive(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String name(String ...name) {
        return "Class";
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
