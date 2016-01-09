package nl.cwi.managed_data_4j.schema.boot.boot_klasses;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import java.util.Collections;
import java.util.Set;

public class NullKlass extends AbstractKlass {

    public static final String NAME = "NULL";

    public NullKlass() {
        this(null);
    }

    public NullKlass(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    public Set<Field> fields(Field... field) {
        return Collections.emptySet();
    }
}