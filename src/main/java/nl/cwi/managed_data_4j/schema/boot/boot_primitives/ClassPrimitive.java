package nl.cwi.managed_data_4j.schema.boot.boot_primitives;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

public class ClassPrimitive extends AbstractPrimitive {

    public ClassPrimitive(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String ...name) {
        return "Class";
    }
}