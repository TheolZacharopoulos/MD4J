package nl.cwi.managed_data_4j.schema.load.models;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class NullKlassImpl extends NullTypeImpl implements Klass {

    public static final String NAME = "NULL";

    public NullKlassImpl() {
        super(null);
    }

    public NullKlassImpl(Schema schema) {
        super(schema);
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
}
