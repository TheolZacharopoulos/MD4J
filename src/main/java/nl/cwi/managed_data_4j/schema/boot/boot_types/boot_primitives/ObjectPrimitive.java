package nl.cwi.managed_data_4j.schema.boot.boot_types.boot_primitives;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

public class ObjectPrimitive extends AbstractPrimitive {

    public ObjectPrimitive(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String ...name) {
        return "Object";
    }
}