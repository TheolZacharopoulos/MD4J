package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.boot.boot_types.AbstractType;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

public class LoadNullType extends AbstractType {

    public static final String NAME = "NULL";

    public LoadNullType() {
        this(null);
    }

    public LoadNullType(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return NAME;
    }
}
