package nl.cwi.managed_data_4j.schema.boot.boot_primitives;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

public class IntegerPrimitive extends AbstractPrimitive {

    public IntegerPrimitive(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String ...name) {
        return "Integer";
    }
}
