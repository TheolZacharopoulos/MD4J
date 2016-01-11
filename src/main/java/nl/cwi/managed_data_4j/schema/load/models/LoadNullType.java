package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.implementation.AbstractType;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

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
