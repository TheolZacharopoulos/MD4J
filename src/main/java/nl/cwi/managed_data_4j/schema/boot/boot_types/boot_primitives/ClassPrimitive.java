package nl.cwi.managed_data_4j.schema.boot.boot_types.boot_primitives;

import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.implementation.AbstractPrimitive;

public class ClassPrimitive extends AbstractPrimitive {

    public ClassPrimitive(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String ...name) {
        return "Class";
    }
}