package nl.cwi.managed_data_4j.schema.boot.boot_klasses;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import java.util.Collections;
import java.util.Set;

public class NullKlass implements Klass {

    public static final String NAME = "NULL";

    private Schema schema;

    public NullKlass() {
        this(null);
    }

    public NullKlass(Schema schema) {
        this.schema = schema;
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    @Override
    public Set<Field> fields(Field...field) {
        return Collections.emptySet();
    }

    @Override
    public Set<Klass> supers(Klass... supers) {
        return Collections.emptySet();
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        return Collections.emptySet();
    }

    @Override
    public Schema schema(Schema... schema) {
        return this.schema;
    }
}
